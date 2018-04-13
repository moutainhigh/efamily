/**   
* @Title: EjlRemindServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-5 下午4:30:28 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.thread.BizMultiThread;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.constant.BizQueue;
import com.winterframework.efamily.entity.EfCustomerAlarmSetting;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfDeviceAlarmTarget;
import com.winterframework.efamily.entity.EfKey;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.LatLng;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfComCustomerService;
import com.winterframework.efamily.service.IEfComOrgDeviceService;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.service.IEfCustomerAlarmSettingService;
import com.winterframework.efamily.service.IEfKeyService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComFamilyUserService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.service.ITskDeviceAlarmService;
import com.winterframework.efamily.service.ITskDeviceAlarmTargetService;
import com.winterframework.efamily.utils.GpsLocationTransformGede;
import com.winterframework.modules.spring.exetend.PropertyConfig;


/**
 *TskDeviceAlarmServiceImpl
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月12日
 */
@Service("tskDeviceAlarmServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class TskDeviceAlarmServiceImpl extends EfDeviceAlarmServiceImpl implements ITskDeviceAlarmService {
	private Logger log = LoggerFactory.getLogger(TskDeviceAlarmServiceImpl.class);
	
	@Resource(name = "redisQueue")
	private RedisQueue redisQueue;
	@Resource(name = "ejlComFamilyUserServiceImpl")
	private IEjlComFamilyUserService ejlComFamilyUserService;
	@Resource(name = "ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService;
	@Resource(name = "efComOrgDeviceServiceImpl")
	private IEfComOrgDeviceService efComOrgDeviceService;
	@Resource(name = "efComOrgServiceImpl")
	private IEfComOrgService efComOrgService;
	@Resource(name = "tskDeviceAlarmTargetServiceImpl")
	private ITskDeviceAlarmTargetService tskDeviceAlarmTargetService;
	@Resource(name = "efKeyServiceImpl")
	private IEfKeyService efKeyService;
	
	@Resource(name="efCustomerAlarmSettingServiceImpl")
	protected IEfCustomerAlarmSettingService efCustomerAlarmSettingService;
	
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeServiceImpl;
	
	@Resource(name="efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerService;
	
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@PropertyConfig(value="sos.key.ak")
	private String ak;
	
	
	 
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(80);
	
	@Override
	public void doProcess() throws BizException {
		String queueName=BizQueue.ALARM;
		int length=redisQueue.len(queueName);
		log.debug("queue alarm length="+length);
		for(int index=0;index<length;index++){
			String value=redisQueue.get(queueName, index);
			if(null==value) break;
			final Long alarmId=Long.valueOf(value); 
			new BizMultiThread(threadPool) {
				protected void doBiz() throws BizException {
					log.debug("process queue alarmId="+alarmId);
					process(alarmId);
				};
			}.start();
			redisQueue.del(queueName, value);
		}
	}
	public void process(Long alarmId)  throws BizException{
		EfDeviceAlarm deviceAlarm=get(alarmId);
		if(null!=deviceAlarm){
			Context ctx=new Context();
			ctx.set("userId", -1);
			try{
				List<EfDeviceAlarmTarget> alarmTargetList=getTargetList(deviceAlarm);
				tskDeviceAlarmTargetService.save(ctx, alarmTargetList);
				deviceAlarm.setStatus(EfDeviceAlarm.Status.HANDLED.getValue());
			}catch(BizException e){
				log.error("device alarm process failed.alarmId="+alarmId,e);
				deviceAlarm.setStatus(EfDeviceAlarm.Status.FAILED.getValue());
			}
			save(ctx, deviceAlarm);
		}
	}
	protected List<EfDeviceAlarmTarget> getTargetList(EfDeviceAlarm deviceAlarm) throws BizException{
		int type=deviceAlarm.getType().intValue();
		List<EfDeviceAlarmTarget> alarmTargetList=new ArrayList<EfDeviceAlarmTarget>();
		log.debug("process queue alarm.getTargetList  alarmId="+deviceAlarm.getId());
		if(type==EfDeviceAlarm.Type.SOS.getValue()){ 
			List<EjlFamilyUser> familyUserList=getFamilyUserAppList(deviceAlarm);
			if(null!=familyUserList){
				for(EjlFamilyUser familyUser:familyUserList){
					/*EfDeviceAlarmTarget alarmTarget=new EfDeviceAlarmTarget();
					alarmTarget.setAlarmId(deviceAlarm.getId());
					alarmTarget.setTargetId(familyUser.getUserId());
					alarmTarget.setIsCustomer(YesNo.NO.getValue());
					Map<String,String> map=new HashMap<String,String>();
					map.put("userId", userDevice.getUserId() + "");
					map.put("watchId", userDevice.getDeviceId() + "");
					map.put("nickName", user.getNickName());
					map.put("icon", user.getHeadImg());
					map.put("battery", this.getBatteryLevel(deviceBatteryRecord.getValue())+"");
					map.put("batteryTime", deviceBatteryRecord.getTime() + "");
					alarmTarget.setValue(JsonUtils.toJson(map));
					alarmTarget.setStatus(YesNo.YES.getValue());
					
					alarmTargetList.add(alarmTarget);*/
				}
			}
			Map<String,String> map=(Map<String,String>)JsonUtils.fromJson(deviceAlarm.getValue(), Map.class);
			String valueParam="location="+map.get("location");
			String location[] = map.get("location").split(",");
			map.remove("location");
			if(location.length>1){
				LatLng latLng = GpsLocationTransformGede.transformFromGCJToWGS(new LatLng(Double.valueOf(location[1]),Double.valueOf(location[0])));
				map.put("alongitude", String.valueOf(latLng.getLongitude()));
				map.put("alatitude", String.valueOf(latLng.getLatitude()));
			}else{
				map.put("alongitude", " ");
				map.put("alatitude", " ");
			}
			map.put("ak", ak);
			EfDeviceAlarmTarget alarmTarget= getAlarmTargetOrg(deviceAlarm, valueParam,map);
			if(null!=alarmTarget){
				alarmTargetList.add(alarmTarget);
			}
		}else if(type==EfDeviceAlarm.Type.BATTERY.getValue()){
			List<EjlFamilyUser> familyUserList=getFamilyUserAppList(deviceAlarm);
			if(null!=familyUserList){
				for(EjlFamilyUser familyUser:familyUserList){
					
				}
			}
			Map<String,String> map=(Map<String,String>)JsonUtils.fromJson(deviceAlarm.getValue(), Map.class);
			String valueParam="battery="+map.get("battery");
			EfDeviceAlarmTarget alarmTarget= getAlarmTargetOrg(deviceAlarm,valueParam,map);
			log.debug("process queue alarm.getAlarmTargetOrg  alarmTarget="+alarmTarget);
			if(null!=alarmTarget){
				alarmTargetList.add(alarmTarget);
			}
		}else if(type==EfDeviceAlarm.Type.FENCE.getValue()){
			List<EjlFamilyUser> familyUserList=getFamilyUserAppList(deviceAlarm);
			if(null!=familyUserList){
				for(EjlFamilyUser familyUser:familyUserList){
					
				}
			}
			Map<String,String> map=(Map<String,String>)JsonUtils.fromJson(deviceAlarm.getValue(), Map.class);
			String valueParam="location="+map.get("location")+"&radius="+map.get("radius")+"&type="+map.get("type");
			EfDeviceAlarmTarget alarmTarget= getAlarmTargetOrg(deviceAlarm,valueParam,map);
			if(null!=alarmTarget){
				alarmTargetList.add(alarmTarget);
			}
		}else if(type==EfDeviceAlarm.Type.HEART.getValue()){
			List<EjlFamilyUser> familyUserList=getFamilyUserAppList(deviceAlarm);
			if(null!=familyUserList){
				for(EjlFamilyUser familyUser:familyUserList){
					
				}
			}
			Map<String,String> map=(Map<String,String>)JsonUtils.fromJson(deviceAlarm.getValue(), Map.class);
			String valueParam="heartRate="+map.get("heartRate")+"&type="+map.get("type");
			EfDeviceAlarmTarget alarmTarget= getAlarmTargetOrg(deviceAlarm,valueParam,map);
			if(null!=alarmTarget){
				alarmTargetList.add(alarmTarget);
			}
		}else if(type==EfDeviceAlarm.Type.BLOOD.getValue()){
/*			List<EjlFamilyUser> familyUserList=getFamilyUserAppList(deviceAlarm);
			if(null!=familyUserList){
				for(EjlFamilyUser familyUser:familyUserList){
					
				}
			}*/
			
			/**
			    imei:设备号码
				systolicPressure ：收缩压
				diastolicPressure：舒张压
				time:时间
				sysType:收缩压类型（1低 2高）
				diaType:舒张压类型（1低 2高） 
			 */
			
			Map<String,String> map=(Map<String,String>)JsonUtils.fromJson(deviceAlarm.getValue(), Map.class);
			String valueParam="systolicPressure="+map.get("systolicPressure")+"&diastolicPressure="+map.get("diastolicPressure")+"&sysType="+map.get("sysType")+"&diaType="+map.get("diaType");
			EfDeviceAlarmTarget alarmTarget= getAlarmTargetOrg(deviceAlarm,valueParam,map);
			if(null!=alarmTarget){
				alarmTargetList.add(alarmTarget);
			}
		}
		return alarmTargetList;
	}
	private EfDeviceAlarmTarget getAlarmTargetOrg(EfDeviceAlarm deviceAlarm,String valueParam,Map<String,String> paramParam) throws BizException {
		EfOrgDevice orgDevice=efComOrgDeviceService.getByDeviceId(deviceAlarm.getDeviceId());
		if(null!=orgDevice){
			EfOrg org=efComOrgService.get(orgDevice.getOrgId());
			if(null!=org){
				EfKey key=efKeyService.getByKey(org.getIkey());
				if(null!=key){
					EfCustomerAlarmSetting alarmSett=efCustomerAlarmSettingService.getValidByCustomerIdAndAlarmType(key.getCustomerId(),deviceAlarm.getType());
					if(null==alarmSett){
						throw new BizException(StatusBizCode.CUSTOMER_ALARM_SETT_MISSING.getValue());
					}
					EfDeviceAlarmTarget alarmTarget=new EfDeviceAlarmTarget();
					alarmTarget.setAlarmId(deviceAlarm.getId());
					alarmTarget.setAlarmType(deviceAlarm.getType());
					alarmTarget.setTargetId(key.getCustomerId());
					alarmTarget.setIsCustomer(YesNo.YES.getValue());
					if(alarmSett.getMethod()==null||alarmSett.getMethod().equals("GET")){
						String value="imei="+orgDevice.getImei()+"&"+valueParam+"&time="+deviceAlarm.getTime();
						alarmTarget.setValue(value);
					}else{
						alarmTarget.setValue(JsonUtils.toJson(paramParam));
					}
					alarmTarget.setStatus(YesNo.NO.getValue());

					return alarmTarget;
				}else{
					log.error("generate alarm target warnning:key is empty.ukey=="+org.getIkey());
				}
			}else{
				log.error("generate alarm target warnning:org is empty.orgId="+orgDevice.getOrgId());
			}
		}else{
			EjlDevice device = ejlComDeviceService.get(deviceAlarm.getDeviceId());
			Qrcode qrcode = qrcodeServiceImpl.getByImei(device.getCode());
			if(qrcode == null){
				throw new BizException(StatusBizCode.DEVICE_UN_VALID.getValue());
			}
			int number = qrcode.getCustomerNumber();
			Long customerId = null;
			try{
			 customerId = efComCustomerService.getEfCustomerBy(number).getId(); 
			}catch(Exception e){
				throw new BizException(StatusBizCode.DEVICE_CUSTOMER_NO_EXIST.getValue());
			}
			EfCustomerAlarmSetting alarmSett=efCustomerAlarmSettingService.getValidByCustomerIdAndAlarmType(customerId,deviceAlarm.getType());
			if(null==alarmSett){
				throw new BizException(StatusBizCode.CUSTOMER_ALARM_SETT_MISSING.getValue());
			}
			EfDeviceAlarmTarget alarmTarget=new EfDeviceAlarmTarget();
			alarmTarget.setAlarmId(deviceAlarm.getId());
			alarmTarget.setAlarmType(deviceAlarm.getType());
			alarmTarget.setTargetId(customerId);  
			alarmTarget.setIsCustomer(YesNo.YES.getValue());
			if(alarmSett.getMethod()==null||alarmSett.getMethod().equals("GET")){
				String value="imei="+device.getCode()+"&"+valueParam+"&time="+deviceAlarm.getTime();
				alarmTarget.setValue(value);
			}else{
				paramParam.put("imei", String.valueOf(device.getCode()));
				paramParam.put("time", String.valueOf(deviceAlarm.getTime()));
				alarmTarget.setValue(JsonUtils.toJson(paramParam));
			}
			alarmTarget.setStatus(YesNo.NO.getValue());

			return alarmTarget;
		}
		return null;
	}
	
	private List<EjlFamilyUser> getFamilyUserAppList(EfDeviceAlarm deviceAlarm) throws BizException{
		EjlUser user=ejlComUserService.get(deviceAlarm.getUserId());
		return ejlComFamilyUserService.getListByFamilyIdAndType(user.getFamilyId(), EjlUser.Type.APP.getCode());
	}
	
}