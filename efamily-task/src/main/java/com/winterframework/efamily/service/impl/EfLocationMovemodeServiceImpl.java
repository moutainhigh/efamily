package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.thread.BizMultiThread;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.ITskLocationSemiDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.entity.EfLocationAssist;
import com.winterframework.efamily.entity.EfLocationMovemode;
import com.winterframework.efamily.entity.EfLocationMovemode.MoveModeSpeed;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthStepCount;
import com.winterframework.efamily.entity.LatLng;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfDeviceSettingService;
import com.winterframework.efamily.service.IEfLocationMovemodeService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComHealthStepCountService;
import com.winterframework.efamily.service.ITskLocationAssistService;
import com.winterframework.efamily.service.ITskLocationSemiServiceNew;
import com.winterframework.efamily.service.ITskLocationService;
import com.winterframework.efamily.utils.LocationUtil;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("efLocationMovemodeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfLocationMovemodeServiceImpl extends EfComLocationMovemodeServiceImpl implements IEfLocationMovemodeService{
	
	
	@Resource(name="tskLocationSemiDaoImpl")
	private ITskLocationSemiDao tskLocationSemiDaoImpl;
	
	@Resource(name = "tskLocationAssistServiceImpl")
	private ITskLocationAssistService tskLocationAssistService;
	
	
	@Resource(name = "ejlComHealthStepCountServiceImpl")
	private IEjlComHealthStepCountService ejlComHealthStepCountService;
	
	@Resource(name = "tskLocationSemiServiceImplAdjust")
	private ITskLocationSemiServiceNew tskLocationSemiServiceImplAdjust;
	@Resource(name = "efLocationMovemodeServiceImpl")
	private IEfLocationMovemodeService efLocationMovemodeService;
	
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	@Resource(name = "redisQueue")
	private RedisQueue redisQueue;   
	
	@Resource(name="notificationUtil")
	protected NotificationUtil notificationUtil;	
	
	
	@Resource(name = "efDeviceSettingServiceImpl")
	private IEfDeviceSettingService efDeviceSettingService;
	
	
	private static int MOVEMODESPLIT=10;
	
	private static int STEPCOUNTSPLIT=30;
	
	private static int STILL_GETLOCATION_FREQUENCY = 30*60;
	private static int STILL_UPLOCATION_FREQUENCY = 30*60;
	private static int MOVE_GETLOCATION_FREQUENCY = 50;
	private static int MOVE_UPLOCATION_FREQUENCY = 60;

	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	@Resource(name="tskLocationServiceImpl")
	private ITskLocationService tskLocationService;
	
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(6);
	
	@Override
	public void doGetMovemode(Map<String,Long> device) throws Exception {
		final Context ctx = new Context();
		ctx.set("userId", -1);
		List<Integer> flags=getValidFlags();
				final Long userId=device.get("userId");
				final Long deviceId=device.get("deviceId");
				EfLocationMovemode efLocationMovemode = this.getLastEfLocationMovemode(userId, deviceId);
				Date lastEndTime = DateUtils.parse(DateUtils.format(DateUtils.currentDate(), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN)+":00", DateUtils.DATETIME_FORMAT_PATTERN);
				Date beginTime = DateUtils.addMinutes(lastEndTime, -MOVEMODESPLIT);
				Date endTime = lastEndTime;
				if(efLocationMovemode!=null){
					if(efLocationMovemode.getMoveMode() == EfLocationMovemode.MoveMode.STILL.getValue()&&efLocationMovemode.getToTime()==null){
						return;
					}
					beginTime = DateUtils.convertLong2Date(efLocationMovemode.getToTime());
					endTime = DateUtils.addMinutes(beginTime, MOVEMODESPLIT);
				}
				while(!endTime.after(lastEndTime)){
					List<EfLocationSemi> locationList = tskLocationSemiDaoImpl.getListByDeviceIdAndTimeFromToAndFlags(userId,deviceId,beginTime,endTime,flags);
					EfLocationMovemode efLocationMovemodeAdd = new EfLocationMovemode();
					efLocationMovemodeAdd.setDeviceId(deviceId);
					efLocationMovemodeAdd.setUserId(userId);
					efLocationMovemodeAdd.setFromTime(DateUtils.convertDate2Long(beginTime));
					efLocationMovemodeAdd.setToTime(DateUtils.convertDate2Long(endTime));
					if(locationList!=null&&locationList.size()>0){
						handlerMovemode(locationList,efLocationMovemodeAdd);
						efLocationMovemode = this.getLastEfLocationMovemode(userId, deviceId);
						if(efLocationMovemode==null||efLocationMovemode.getToTime()!=null){
							List<EfLocationMovemode> list = new ArrayList<EfLocationMovemode>();
							list.add(efLocationMovemodeAdd);
							this.save(ctx, list);
						}
					}else{
						efLocationMovemode = this.getLastEfLocationMovemode(userId, deviceId);
						if(efLocationMovemode == null || efLocationMovemode.getToTime()!=null){
							efLocationMovemodeAdd.setMaxSpeed(0d);
							efLocationMovemodeAdd.setMinSpeed(0d);
							efLocationMovemodeAdd.setAvgSpeed(0d);
							efLocationMovemodeAdd.setRemark("没有定位数据");
							efLocationMovemodeAdd.setMoveMode(EfLocationMovemode.MoveMode.STOP.getValue());
							List<EfLocationMovemode> list = new ArrayList<EfLocationMovemode>();
							list.add(efLocationMovemodeAdd);
							this.save(ctx, list);
						}
					}
					beginTime = endTime;
					endTime = DateUtils.addMinutes(beginTime, MOVEMODESPLIT);
				}
	}
	
	private long getAvgStepCount(Long userId,Long deviceId,Long beginTime,Long endTime) throws Exception{
		long count = 0;
		final Context ctx = new Context();
		ctx.set("userId", -1);
		EjlHealthStepCount entity = new EjlHealthStepCount();
		entity.setUserId(userId);
		entity.setDeviceId(deviceId);
		//只看最近20分钟的数据
		if(DateUtils.calcMinutesBetween(DateUtils.convertLong2Date(beginTime), DateUtils.convertLong2Date(endTime))>20){
			beginTime = DateUtils.convertDate2Long(DateUtils.addMinutes(DateUtils.convertLong2Date(endTime), -20));
		}
		entity.setBegintime(DateUtils.convertLong2Date(beginTime));
		entity.setEndtime(DateUtils.convertLong2Date(endTime));
		entity.setType(1);
		Long result = 0l;
		Long times =0l;
		
		List<EjlHealthStepCount> list = ejlComHealthStepCountService.selectListObjByAttribute(ctx, entity);
		
		if(list != null&&list.size()>0){
			for(EjlHealthStepCount ejlHealthStepCount:list){
				count +=ejlHealthStepCount.getSteps();
				times+= DateUtils.calcSecondsBetween(ejlHealthStepCount.getBegintime(), ejlHealthStepCount.getEndtime());
			}
			times = times/60;
			//至少运动了大于1分钟
			if(times>1){
				result =  count/times;
			}
		}
		return result;
	}
	
	
	@Override
	public void doStill(EfLocationMovemode efLocationMovemode) throws Exception {
		//查询没有判断静止的设备，用于判断是否静止
		List<EfLocationMovemode> list = this.getEntityDao().getMoveDeviceList();
		final Context ctx = new Context();
		ctx.set("userId", -1);
				if(efLocationMovemode.getMoveMode() == EfLocationMovemode.MoveMode.STOP.getValue()||efLocationMovemode.getMoveMode() == EfLocationMovemode.MoveMode.STILL.getValue()){
					//最后2个点都是静止
					List<EfLocationMovemode> stopList = this.getEntityDao().getLastEfLocationMovemodeList(efLocationMovemode.getUserId(), efLocationMovemode.getDeviceId(), 2);
					if(stopList!=null && stopList.size()>=2&&(stopList.get(0).getMoveMode() == EfLocationMovemode.MoveMode.STOP.getValue()||stopList.get(0).getMoveMode() == EfLocationMovemode.MoveMode.STILL.getValue())&&stopList.get(0).getMoveMode()==stopList.get(1).getMoveMode()){
						//计步数据小于规定值
						if(getAvgStepCount(efLocationMovemode.getUserId(),efLocationMovemode.getDeviceId(),stopList.get(1).getFromTime(),stopList.get(0).getToTime())<STEPCOUNTSPLIT){
							if(DateUtils.calcMinutesBetween(DateUtils.convertLong2Date(stopList.get(0).getToTime()), DateUtils.currentDate())>10){
								return;
							}
							//插入一条绝对静止的数据
							EfLocationMovemode efLocationMovemodeAdd = new EfLocationMovemode();
							efLocationMovemodeAdd.setDeviceId(efLocationMovemode.getDeviceId());
							efLocationMovemodeAdd.setUserId(efLocationMovemode.getUserId());
							efLocationMovemodeAdd.setFromTime(stopList.get(0).getToTime());
							efLocationMovemodeAdd.setToTime(null);
							efLocationMovemodeAdd.setAvgSpeed(0d);
							efLocationMovemodeAdd.setMaxSpeed(0d);
							efLocationMovemodeAdd.setMinSpeed(0d);
							efLocationMovemodeAdd.setRemark("改成静止");
							efLocationMovemodeAdd.setMoveMode(EfLocationMovemode.MoveMode.STILL.getValue());
							List<EfLocationMovemode> list1 = new ArrayList<EfLocationMovemode>();
							list1.add(efLocationMovemodeAdd);
							this.save(ctx, list1);
							this.changeDeviceFrequencyTo(efLocationMovemode.getUserId(), efLocationMovemode.getDeviceId(), EfLocationMovemode.MoveMode.STILL.getValue());
						}
					
				
			}
		}
		
	}





	@Override
	public void doMovement(EfLocationMovemode efLocationMovemode) throws Exception {
		//查询已经静止的设备，用于判断是否运动
		final Context ctx = new Context();
		ctx.set("userId", -1);
				if(getAvgStepCount(efLocationMovemode.getUserId(),efLocationMovemode.getDeviceId(),efLocationMovemode.getFromTime(),DateUtils.getCurTime())>=20){
					efLocationMovemode.setToTime(DateUtils.getCurTime());
					efLocationMovemode.setRemark("改成运动");
					List<EfLocationMovemode> list1 = new ArrayList<EfLocationMovemode>();
					list1.add(efLocationMovemode);
					this.save(ctx, list1);
					this.changeDeviceFrequencyTo(efLocationMovemode.getUserId(), efLocationMovemode.getDeviceId(), EfLocationMovemode.MoveMode.WALK.getValue());
				}else{
				Long stillBeginTime = efLocationMovemode.getFromTime();
				List<EfLocationSemi> locationList = tskLocationSemiDaoImpl.getListByDeviceIdAndTimeFromToAndFlags(efLocationMovemode.getUserId(),efLocationMovemode.getDeviceId(),DateUtils.convertLong2Date(stillBeginTime),DateUtils.currentDate(),this.getValidFlags());
				if(locationList!=null&&locationList.size()>1){
					//?????????tskLocationSemiServiceImplAdjust.aggregationTmp(ctx, locationList);
					if(locationList.size()>1){
						efLocationMovemode.setToTime(DateUtils.convertDate2Long(locationList.get(0).getTimeEnd()));
						efLocationMovemode.setRemark("改成运动");
						List<EfLocationMovemode> list1 = new ArrayList<EfLocationMovemode>();
						list1.add(efLocationMovemode);
						this.save(ctx, list1);
						this.changeDeviceFrequencyTo(efLocationMovemode.getUserId(), efLocationMovemode.getDeviceId(), EfLocationMovemode.MoveMode.WALK.getValue());
					}
				}}
	}
	
	 public void pushDevice(Long userId,Long deviceId,Map<String,String> data,int command,NotyMessage.Type nt,boolean isRealTime) throws BizException {
    	 try{
	    	 Notification notification = new Notification();
			 if(data==null){
		        data = new HashMap<String,String>();
		     }
	 		 log.info("操作设备，推送命令给设备： userId = "+userId+" ; deviceId = "+deviceId+" ; command = "+command+" ; data = "+data.toString()); 
			 NotyTarget target = new NotyTarget(userId,deviceId);	//推送目标 
			 NotyMessage message = new NotyMessage();	//推送消息
			 message.setCommand(command);// 更换：20105  解除：20106
			 message.setData(data);
			 //message.setType(NotyMessage.Type.NOTICE);
			 message.setType(nt);
			 notification.setRealTime(isRealTime); //是否实时推送
			 notification.setMessage(message);
			 notification.setTarget(target);
			 notificationUtil.notification(notification);
    	 }catch(Exception e){
    		 log.error("推送异常：",e);
    		 throw new BizException(StatusBizCode.PUSH_FAILED.getValue());
    	 }
     } 

	 
	 
	 public void changeDeviceFrequencyTo(Long userId,Long deviceId,int model) throws Exception{
		 EfDeviceSetting deviceSetting=efDeviceSettingService.getByUserIdAndDeviceId(userId,deviceId);
		 final Context ctx = new Context();
			ctx.set("userId", -1);
			if(deviceSetting == null){
				deviceSetting = new EfDeviceSetting();
				deviceSetting.setDeviceId(deviceId);
				deviceSetting.setUserId(userId);
			}
			DeviceParamFrequency deviceParamFrequency = null;
			String frequency= deviceSetting.getFrequency();
			if(frequency != null){
				deviceParamFrequency = JsonUtils.fromJson(frequency, DeviceParamFrequency.class);
			}else{
				deviceParamFrequency = new DeviceParamFrequency();
			}
			if(EfLocationMovemode.MoveMode.STILL.getValue()==model){
				deviceParamFrequency.setLocationGfreq(STILL_GETLOCATION_FREQUENCY);
				deviceParamFrequency.setLocationUfreq(STILL_UPLOCATION_FREQUENCY);
			}
			
			if(EfLocationMovemode.MoveMode.WALK.getValue()==model){
				deviceParamFrequency.setLocationGfreq(MOVE_GETLOCATION_FREQUENCY);
				deviceParamFrequency.setLocationUfreq(MOVE_UPLOCATION_FREQUENCY);
			}
			String frequencyStr=JsonUtils.toJson(deviceParamFrequency);
			deviceSetting.setFrequency(frequencyStr);
			efDeviceSettingService.save(ctx, deviceSetting);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
			map.put("code", EfDeviceSetting.ModuleCode.FREQUENCY.getValue()+"");
			map.put("value", frequencyStr);
			pushDevice(userId, deviceId, map, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT, false);
	 }


	private void handlerMovemode(List<EfLocationSemi> locationList,EfLocationMovemode efLocationMovemode) throws BizException{
		sort(locationList,true);
		List<Double> speeds = new ArrayList<Double>();
		if(locationList == null || locationList.size()==0){
			return;
		}
		Double maxSpeed=null;
		Double minSpeed=null;
		Double totalSpeeds=0d;
		for(EfLocationSemi efLocationSemi:locationList){
			EfLocationAssist efLocationAssist = tskLocationAssistService.getByLocationId(efLocationSemi.getId());
			totalSpeeds += efLocationAssist.getSpeed();
			speeds.add(efLocationAssist.getSpeed());
			maxSpeed = maxSpeed==null?efLocationAssist.getSpeed():maxSpeed<efLocationAssist.getSpeed()?efLocationAssist.getSpeed():maxSpeed;
			minSpeed = minSpeed==null?efLocationAssist.getSpeed():minSpeed>efLocationAssist.getSpeed()?efLocationAssist.getSpeed():minSpeed;
		}
		efLocationMovemode.setAvgSpeed(totalSpeeds/locationList.size());
		efLocationMovemode.setMaxSpeed(maxSpeed);
		efLocationMovemode.setMinSpeed(minSpeed);
		efLocationMovemode.setMoveMode(getMoveModeBySpeed(efLocationMovemode.getAvgSpeed()).getMoveMode());
		if(speeds.size()/3>=1){
			int size = speeds.size()/3;
			double avgSpeed1 = 0;
			for(int i=0;i<size;i++){
				avgSpeed1 +=speeds.get(i);
			}
			
			double avgSpeed2 = 0;
			
			for(int i=size;i<size*2;i++){
				avgSpeed2+=speeds.get(i);
			}
			
			double avgSpeed3=0;
			int j=0;
			for(int i=size*2;i<speeds.size();i++){
				j++;
				avgSpeed3+=speeds.get(i);
			}
			int model3 = getMoveModeBySpeed(avgSpeed3/j).getMoveMode();
			if((avgSpeed1>avgSpeed2&&avgSpeed2>avgSpeed3)||(avgSpeed1<=avgSpeed2&&avgSpeed2<avgSpeed3)){
				efLocationMovemode.setMoveMode(model3);
			}
		}
		
		
	}
	
	
	public MoveModeSpeed getMoveModeBySpeed(Double speed){
		List<MoveModeSpeed> moveMoveList = Arrays.asList(EfLocationMovemode.MoveModeSpeed.values());
		for(MoveModeSpeed moveModeSpeed:moveMoveList){
			if(moveModeSpeed.isMoveMode(speed)){
				return moveModeSpeed;
			}
		}
		return null;
	}
	
	
	/**
	 * @param locationSemiList
	 * @param isAsc
	 */
	public void sort(List<EfLocationSemi> locationSemiList,final boolean isAsc){
		if(null!=locationSemiList && locationSemiList.size()>0){
			Collections.sort(locationSemiList, 
			new Comparator<EfLocationSemi>(){
				int k=isAsc?1:-1;
				@Override
				public int compare(EfLocationSemi o1, EfLocationSemi o2) {
					if(o1.getTime().after(o2.getTime())){
						return 1*k;
					}
					return -1*k;
				}
			});
		}
	}
	
	/**
	 * 获取有效点标识值
	 * @return
	 */
	protected List<Integer> getValidFlags(){
		List<Integer> flags=new ArrayList<Integer>();
		List<EfLocationSemi.Flag> sf=Arrays.asList(EfLocationSemi.Flag.values());
		for(EfLocationSemi.Flag f:sf){
			if(!f.equals(EfLocationSemi.Flag.DELETE) && !f.equals(EfLocationSemi.Flag.DISPOSE)&&f.getValue()>EfLocationSemi.Flag.SUSPECT.getValue()) {
				flags.add(f.getValue());
			}
		}
		return flags;
	}


	@Override
	public double getJuheDistance(Long userId, Long deviceId) throws BizException {
		Double juheDistance = LocationUtil.JUHEDISTANCE;
		/*轨迹新算法完成之前暂去掉
		 * try{
		EfLocationMovemode efLocationMovemode = this.getLastEfLocationMovemode(userId, deviceId);
		if(efLocationMovemode == null){
			return juheDistance;
		}
		List<EfLocationMovemode.JuheDistance> list = Arrays.asList(EfLocationMovemode.JuheDistance.values());
		for(EfLocationMovemode.JuheDistance j:list){
			if(efLocationMovemode.getMoveMode().intValue() == j.getValue()){
				return j.getDistance();
			}
		}}catch(Exception e){
			log.error("getJuheDistance error", e);
		}*/
		return juheDistance;
	}
	@Override
	public void processMoveMode() throws BizException {
		//List<EjlDevice> deviceList=getDeviceList();
		List<EjlDevice> deviceList=ejlComDeviceService.getDeviceByTypeAndStatus(EjlDevice.Type.WATCH.getValue(),1);
		if(null!=deviceList){
			for(EjlDevice device:deviceList){
				final Long userId=device.getUserId();
				final Long deviceId=device.getId();
				if(userId == null || userId==0){
					continue;
				}
				new BizMultiThread(threadPool) {
					protected void doBiz() throws BizException {
						log.debug("move2still process userId"+userId+" deviceId="+deviceId);
						efLocationMovemodeService.doProcessMoveMode(userId,deviceId);
					};
				}.start();
			}
		}
	}
	
	List<EjlDevice> getDeviceList(){
		List<EjlDevice> s=new ArrayList<EjlDevice>();
		EjlDevice device=new EjlDevice();
		device.setUserId(3013l);
		device.setId(443l);
		s.add(device);
		return s;
		
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class,readOnly = false)
	public void doProcessMoveMode(Long userId,Long deviceId) throws BizException{
		EfLocationMovemode lastLocationMoveMode=getLastEfLocationMovemode(userId, deviceId);
		if(null==lastLocationMoveMode || lastLocationMoveMode.getMoveMode().intValue()!=EfLocationMovemode.MoveMode.STILL.getValue()){
			/**
			 * 1.获取所有设备（分批）
			 * 2.判断是否已经静止
			 * 3.判断计步(15分钟内没有单次（60s）超过20步的计步记录)
			 * 4.判断定位(10分钟内定位数据只有一个点)
			 * 5.更改运动模式
			 */
			doMove2Still(userId,deviceId,lastLocationMoveMode);
		}else{
			/**
			 * 1.获取所有设备（分批）
			 * 2.判断是否已经非静止
			 * 3.判断定位数据（实时定位产生）
			 * 4.判断计步(3分钟内有计步记录)
			 * 5.判断定位(触发实时获取定位数据)
			 * 6.更改运动模式
			 */
			doStill2Move(userId,deviceId,lastLocationMoveMode);
		}
	}
	private enum ChangeType{
		STILL(1,1800,1800),MOVE(2,65,330);
		private int _v;
		private int _gf;
		private int _uf;
		private ChangeType(int v,int gf,int uf){
			_v=v;
			_gf=gf;
			_uf=uf;
		}
		public int getValue(){
			return _v;
		}
		public int getGFreq(){
			return _gf;
		}
		public int getUFreq(){
			return _uf;
		}
	}
	protected void doMove2Still(Long userId,Long deviceId,EfLocationMovemode lastLocationMoveMode) throws BizException{
		final Integer STEP_MIN=15; //分钟数范围内计步
		final Integer STEP_COUNT=20; //计步数
		final Integer DELAY_MIN=10;	//允许定位数据延迟分钟数
		final Integer LOC_MIN=30;	//分钟数范围内定位 
		final Integer LATEST_MIN=1; //距离当前时间分钟数
		Date nowTime=DateUtils.currentDate();
		/*if(lastLocationMoveMode !=null){
			//运动时间少于15分钟，不进行静止判断
			if(DateUtils.addMinutes(DateUtils.convertLong2Date(lastLocationMoveMode.getFromTime()), 15).compareTo(nowTime)>0){
				return;
			}
		}*/
		//Date nowTime=DateUtils.getDate(1478374335000L);
		Date fromTime=DateUtils.addMinutes(nowTime, -1*STEP_MIN);

		List<EjlHealthStepCount> stepList=ejlComHealthStepCountService.getListByUserIdDeviceIdBetweenTime(userId, deviceId, fromTime, nowTime);
		boolean hasStep=false;
		boolean hasOnlyLoc=false;
		String curLocation="";
		if(null!=stepList){
			for(EjlHealthStepCount step:stepList){
				//高频 低频
				Long costSecds=DateUtils.calcSecondsBetween(step.getBegintime(), step.getEndtime());
				if((step.getSteps()*60/costSecds)>=STEP_COUNT){ //每分钟
					hasStep=true;
					break;
				}
			}
		}
		log.debug("toStill deviceId:"+deviceId +" hasStep:"+hasStep);
		if(!hasStep){
			Date time=DateUtils.addMinutes(nowTime, -1*(LOC_MIN+DELAY_MIN));
			List<EfLocationSemi> locationSemiList=tskLocationSemiServiceImplAdjust.getValidListAfterAggredByUserIdAndDeviceIdAndTimeFromTo(userId, deviceId, time, nowTime);
			if(null!=locationSemiList&&locationSemiList.size()>0){
				log.debug("toStill deviceId:"+deviceId +" locationSize:"+locationSemiList.size());
				LatLng ll=null;
				boolean hasMoreLoc=false;
				boolean hasLatestLoc=false;
				if(locationSemiList.size()==1 && DateUtils.calcMinutesBetween(locationSemiList.get(0).getTimeBegin(), locationSemiList.get(0).getTimeEnd())>=(LOC_MIN)){
					curLocation=new LatLng(locationSemiList.get(0).getLocation()).toString();
					hasOnlyLoc=true;
				}else{
					for(EfLocationSemi locationSemi:locationSemiList){
						if(null==ll){
							ll=new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude()); 
							curLocation=ll.toString();
							continue;
						}
						/*if(DateUtils.calcMinutesBetween(DateUtils.addSeconds(location.getTime(), location.getTimeStay()),nowTime)<=(DELAY_MIN+LATEST_MIN)){
							hasLatestLoc=true;
						}*/
						if(DateUtils.calcMinutesBetween(locationSemi.getTimeEnd(),nowTime)<=(DELAY_MIN+LATEST_MIN)){
							hasLatestLoc=true;
						}
						/*if(!ll.equals(new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude()))){
							hasMoreLoc=true;
							break;
						};*/
						
						if(LocationUtil.getDistance(ll, new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude()))>500){
							log.debug("userId local >500 deviceId:"+deviceId+" curLocation:"+curLocation+" id:"+locationSemi.getId());
							hasMoreLoc=true;
							break;
						}
						
						log.debug("toStill deviceId:"+deviceId+" hasMoreLoc|hasLatestLoc:"+hasMoreLoc+"|"+hasLatestLoc +" timeEnd"+locationSemi.getTimeEnd());
					}
					if(!hasMoreLoc && hasLatestLoc){
						hasOnlyLoc=true;
					}
				}
			}
		}
		log.debug("toStill deviceId:"+deviceId+" userId hasOnlyLoc:"+hasOnlyLoc +"userId:"+userId+" deviceId:"+deviceId);
		if(hasOnlyLoc){
			changeMoveMode(ChangeType.STILL, userId, deviceId, nowTime,lastLocationMoveMode,curLocation);
		}else if(lastLocationMoveMode == null){
			changeMoveMode(ChangeType.MOVE, userId, deviceId, nowTime,lastLocationMoveMode,curLocation);
		}
	}
	protected void doStill2Move(Long userId,Long deviceId,EfLocationMovemode lastLocationMoveMode) throws BizException{
		final Integer STEP_COUNT=10; //计步数
		final Integer DELAY_MIN=5;	//允许定位数据延迟分钟数
		final Integer LOC_MIN=10;	//分钟数范围内定位 
		final Integer LOC_COUNT=2; //定位数
		final Integer STEP_MIN=10;
		
		boolean needChangeMode=false;
		Date nowTime=DateUtils.currentDate();
		//Date nowTime=DateUtils.parse("2016-11-11 19:01:33", "yyyy-MM-dd HH:mm");
		Date time=DateUtils.addMinutes(nowTime, -1*(LOC_MIN+DELAY_MIN));
		//List<EfLocationSemi> locationSemiList=tskLocationSemiServiceImplAdjust.getValidListByUserIdAndDeviceIdAndTimeFromTo(userId, deviceId,DateUtils.getDate(lastLocationMoveMode.getFromTime()),nowTime);
		//静到动 是零散的实时定位  处理之后很难保留下来 故放开条件：查询33的点
		//排除聚合删除的点  如果是聚合的点再生成聚合点 则会满足相同的time有多个点满足条件
		List<EfLocationSemi> locationSemiList=tskLocationSemiServiceImplAdjust.getStill2MoveListByUserIdAndDeviceIdAndTimeFromTo(userId, deviceId,DateUtils.getDate(lastLocationMoveMode.getFromTime()),nowTime);
		//List<EfLocationSemi> locationSemiList=tskLocationSemiServiceImplAdjust.getListByUserIdAndDeviceIdAndTimeFromTo(userId, deviceId,DateUtils.getDate(lastLocationMoveMode.getFromTime()),nowTime);
		String curLocation="";
		if(null!=locationSemiList){
			int count=0;
			sort(locationSemiList, true);//判断相邻点间的时间间隔
			EfLocationSemi lastLocationSemi=null;
			for(EfLocationSemi locationSemi:locationSemiList){
				boolean needCount=true;
				String lastLoc=lastLocationMoveMode.getCurLocation();
				LatLng latlng=new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude());
				curLocation=latlng.toString();
				//静到动 改100米  主要是计步触发  一般步行200米后极有可能实际已经改变了运动模式（非步行） 这样很难会再有定位过来 则永远改变不了运动模式
				if(LocationUtil.getDistance(new LatLng(Double.valueOf(lastLoc.split(",")[1]),Double.valueOf(lastLoc.split(",")[0])), latlng)>100){
					if(null!=lastLocationSemi){
						//加条件:count点之间的时间间隔不是太长
						if(DateUtils.calcMinutesBetween(lastLocationSemi.getTime(),locationSemi.getTime())>STEP_MIN){
							needCount=false;
						}
					}
					lastLocationSemi=locationSemi;
					if(needCount){
						count++;
					}
					if(count>=LOC_COUNT){
						needChangeMode=true;
						break;
					}
				}
			}
		}
		log.debug("toMove deviceId:"+deviceId +" locationSize:"+locationSemiList.size()+" needChangeMode:"+needChangeMode);
		if(needChangeMode){
			changeMoveMode(ChangeType.MOVE,userId,deviceId,nowTime,lastLocationMoveMode,curLocation);
		}else{
			Date fromTime=DateUtils.addMinutes(nowTime, -1*STEP_MIN);
			List<EjlHealthStepCount> stepList=ejlComHealthStepCountService.getListByUserIdDeviceIdBetweenTime(userId, deviceId, fromTime, nowTime);
			boolean hasStep=false;
			
			if(null!=stepList){
				for(EjlHealthStepCount step:stepList){
					Long costSecds=DateUtils.calcSecondsBetween(step.getBegintime(), step.getEndtime());
					if((step.getSteps()*60/costSecds)>=STEP_COUNT){
						hasStep=true;
						break;
					}
				}
			}
			if(hasStep){
				log.debug("toMove deviceId:"+deviceId +" send getMessage");
				triggerLocation(userId,deviceId);
			}
		}
	}
	
	protected void triggerLocation(Long userId,Long deviceId) throws BizException{
		notificationUtil.notification(userId, deviceId, 20626,NotyMessage.Type.OPER, null, true);
	}
	
	protected void changeMoveMode(ChangeType type,Long userId,Long deviceId,Date curTime,EfLocationMovemode lastLocationMoveMode,String curLocation) throws BizException{
		Context ctx = new Context();
		ctx.set("userId", -1);
		if(null!=lastLocationMoveMode && null==lastLocationMoveMode.getToTime()){
			lastLocationMoveMode.setToTime(curTime.getTime());
			save(ctx, lastLocationMoveMode);
		}
		Integer moveMode=type.equals(ChangeType.STILL)?EfLocationMovemode.MoveMode.STILL.getValue():EfLocationMovemode.MoveMode.WALK.getValue();
		EfLocationMovemode locationMoveMode = new EfLocationMovemode();
		locationMoveMode.setUserId(userId);
		locationMoveMode.setDeviceId(deviceId);
		locationMoveMode.setMoveMode(moveMode);
		locationMoveMode.setAvgSpeed(0.0);
		locationMoveMode.setMaxSpeed(0.0);
		locationMoveMode.setMinSpeed(0.0);
		locationMoveMode.setFromTime(curTime.getTime());
		locationMoveMode.setCurLocation(curLocation);
		
		save(ctx, locationMoveMode);
		
		
		changeFrequency(userId, deviceId, type);
		changeLocationOnff(userId,deviceId,moveMode);
	}
	protected void changeLocationOnff(Long userId,Long deviceId,Integer moveMode) throws BizException{
		int onff=YesNo.NO.getValue();
		if(moveMode.intValue()==EfLocationMovemode.MoveMode.STILL.getValue()){
			//off
			onff=YesNo.NO.getValue();
		}else{
			//on
			onff=YesNo.YES.getValue();
		}
		EfDeviceSetting deviceSettingNew=new EfDeviceSetting(); 
		deviceSettingNew.setDeviceId(deviceId);
		deviceSettingNew.setUserId(userId); 
		DeviceParamCommon paramComm=new DeviceParamCommon();
		paramComm.setLocationOnff(onff);
		String deviceCommStr=JsonUtils.toJson(paramComm);
		deviceSettingNew.setCommon(deviceCommStr);
		Context ctx = new Context();
		ctx.set("userId", -1);
		efDeviceSettingService.save(ctx, deviceSettingNew);
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("code", EfDeviceSetting.ModuleCode.COMMON.getValue()+"");
		data.put("value", deviceCommStr);
		notificationUtil.notification(userId, deviceId, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT, data, false);
	}
	protected void changeFrequency(Long userId,Long deviceId,ChangeType type) throws BizException{
		Integer locationGfreq=type.getGFreq();
		Integer locationUfreq=type.getUFreq();
		/*EfDeviceSetting deviceSetting=efDeviceSettingService.getByUserIdAndDeviceId(userId,deviceId);
		DeviceParamFrequency paramFreq=null;
		if(null==deviceSetting){
			deviceSetting = new EfDeviceSetting();
			deviceSetting.setDeviceId(deviceId);
			deviceSetting.setUserId(userId); 
		}else{
			paramFreq=JsonUtils.fromJson(deviceSetting.getFrequency(),DeviceParamFrequency.class);
		}
		if(null==paramFreq){
			paramFreq=new DeviceParamFrequency();
		}*/
		EfDeviceSetting deviceSettingNew=new EfDeviceSetting(); 
		deviceSettingNew.setDeviceId(deviceId);
		deviceSettingNew.setUserId(userId); 
		DeviceParamFrequency paramFreq=new DeviceParamFrequency();
		paramFreq.setLocationGfreq(locationGfreq);
		paramFreq.setLocationUfreq(locationUfreq);
		String deviceFreqStr=JsonUtils.toJson(paramFreq);
		deviceSettingNew.setFrequency(deviceFreqStr);
		Context ctx = new Context();
		ctx.set("userId", -1);
		efDeviceSettingService.save(ctx, deviceSettingNew);
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("code", EfDeviceSetting.ModuleCode.FREQUENCY.getValue()+"");
		data.put("value", deviceFreqStr);
		notificationUtil.notification(userId, deviceId, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT, data, false);
	}
}
