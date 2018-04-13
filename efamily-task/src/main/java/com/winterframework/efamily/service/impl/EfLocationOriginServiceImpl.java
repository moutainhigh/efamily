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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.thread.BizMultiThread;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.entity.CityJsonObject;
import com.winterframework.efamily.entity.DeviceMobile;
import com.winterframework.efamily.entity.EfLocationGps;
import com.winterframework.efamily.entity.EfLocationOrigin;
import com.winterframework.efamily.entity.EfLocationWifi;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.GaoDeLocationStruc;
import com.winterframework.efamily.entity.LatLng;
import com.winterframework.efamily.entity.Regeocode;
import com.winterframework.efamily.service.IDeviceMobileService;
import com.winterframework.efamily.service.IDeviceSignalRecordService;
import com.winterframework.efamily.service.IEfComLocationGpsService;
import com.winterframework.efamily.service.IEfComLocationOriginService;
import com.winterframework.efamily.service.IEfLocationOriginService;
import com.winterframework.efamily.service.IEfLocationWifiService;
import com.winterframework.efamily.service.IEjlComLocationService;
import com.winterframework.efamily.service.ITskLocationSemiServiceNew;


import com.winterframework.efamily.utils.BaiDuGetCityNameUtil;
import com.winterframework.efamily.utils.Constant;
import com.winterframework.efamily.utils.GaoDeLocationGetUtil;
import com.winterframework.efamily.utils.GpsLocationTransformGede;
import com.winterframework.efamily.utils.LocationUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;


/** 
* @ClassName: EfLocationOriginServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-5 下午4:30:28 
*  
*/
@Service("efLocationOriginServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfLocationOriginServiceImpl extends EfComLocationOriginServiceImpl implements IEfLocationOriginService {
	private Logger log = LoggerFactory.getLogger(EfLocationOriginServiceImpl.class);
	
	@Resource(name = "deviceMobileServiceImpl")
	private IDeviceMobileService deviceMobileServiceImpl;
	
	@Resource(name = "efLocationWifiServiceImpl")
	private IEfLocationWifiService efLocationWifiServiceImpl;
	
	@Resource(name = "ejlComDeviceDaoImpl")
	private IEjlComDeviceDao ejlComDeviceDaoImpl;
	
	@Resource(name = "deviceSignalRecordServiceImpl")
	private IDeviceSignalRecordService deviceSignalRecordServiceImpl;

	@Resource(name = "ejlComLocationServiceImpl")
	private IEjlComLocationService ejlComLocationServiceImpl;
	
	
	@Resource(name = "efComLocationGpsServiceImpl")
	private IEfComLocationGpsService efComLocationGpsServiceImpl;
	
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@PropertyConfig(value = "gaode.location.key")
	private String key;
	
	@Resource(name = "tskLocationSemiServiceImplNew")
	private ITskLocationSemiServiceNew tskLocationSemiServiceNew;
	
	@Resource(name = "efComLocationOriginServiceImpl")
	private IEfComLocationOriginService efComLocationOriginServiceImpl;
	
	
	private static Integer EACH_PRO_MINUTES=360;
	
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(80);
	
	
	@Override
	public void initLocationOrigin() throws Exception {
		final int days=1;	//1天之内 根据实际有意义的轨迹要求(24小时)++天数
		final Date timeTo=DateUtils.addMinutes(DateUtils.currentDate(),-1);
		final Date timeFrom=DateUtils.addDays(timeTo, -1*days);
		
		
		log.debug("process start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		//获取用户设备
		List<Map<String,Long>> deviceList = efComLocationGpsServiceImpl.getNeedHandleDeviceList(timeFrom, timeTo);
		deviceList.addAll(efLocationWifiServiceImpl.getNeedHandleDeviceList(timeFrom, timeTo));
		deviceList.addAll(deviceMobileServiceImpl.getNeedHandleDeviceList(timeFrom, timeTo));
		List<String> handleList = new ArrayList<String>();
		for(Map<String,Long> device:deviceList){
			final Long userId=device.get("userId");
			final Long deviceId=device.get("deviceId");
			//用户设备有重复，新加key过滤重复的用户和设备
			String key = userId.longValue()+"-"+deviceId.longValue();
			if(handleList.contains(key)){
				continue;
			}
			this.handlerLocationOrigin(userId, deviceId, timeFrom, timeTo, threadPool);
			handleList.add(key);
		}
		
		/*Map<Date,Date> dateMap = efComLocationGpsServiceImpl.getUnhandledMaxMinTime(queryMaxDayNum);
		
		
		//List<Map<String,Long>> deviceList=getNeedHandleDeviceList(timeFrom,timeTo);
		List<Date> beginDateList = new ArrayList<Date>();
		List<Date> endDateList = new ArrayList<Date>();
		if(dateMap!=null&&!dateMap.isEmpty()){
			beginDateList.add(dateMap.get("minTime"));
			endDateList.add(dateMap.get("maxTime"));
		}
		dateMap = efLocationWifiServiceImpl.getUnhandledMaxMinTime(queryMaxDayNum);
		if(dateMap!=null&&!dateMap.isEmpty()){
			beginDateList.add(dateMap.get("minTime"));
			endDateList.add(dateMap.get("maxTime"));
		}
		dateMap = deviceMobileServiceImpl.getUnhandledMaxMinTime(queryMaxDayNum);
		if(dateMap!=null&&!dateMap.isEmpty()){
			beginDateList.add(dateMap.get("minTime"));
			endDateList.add(dateMap.get("maxTime"));
		}
		
		Collections.sort(beginDateList);
		Collections.sort(endDateList);
		if(beginDateList.size()==0){
			return;
		}
		Date beginDate = beginDateList.get(0);
		Date endDate = endDateList.get(endDateList.size()-1);
		List<List<Date>> dateSplitList = LocationUtil.dateSplit(beginDate, endDate, EACH_PRO_MINUTES);
		for(List<Date> dateList:dateSplitList){
			list.addAll(this.getCellGbrsLocation(baseUrl,dateList.get(0),dateList.get(1)));
			list.addAll(this.getGpsLocation(dateList.get(0),dateList.get(1)));
			list.addAll(this.getWiftLocation(baseUrl,dateList.get(0),dateList.get(1)));
			this.locationListSort(list);
			Context ctx = new Context();
			ctx.set("userId", -1);
			this.save(ctx, list);
		}*/
	}
	
	
	public int batchInsert(Context ctx,List<EfLocationOrigin> entityList) throws BizException{
		return this.save(ctx, entityList);
	}
	
	public void locationListSort(List<EfLocationOrigin> cellList){
		Collections.sort(cellList,new Comparator(){
			@Override
			public int compare(Object o1, Object o2) {
				EfLocationOrigin e1 = (EfLocationOrigin)o1;
				EfLocationOrigin e2 = (EfLocationOrigin)o2;
				if(e1.getDeviceId().longValue()>e2.getDeviceId().longValue()){
					return 1;
				}else if(e1.getDeviceId().longValue()<e2.getDeviceId().longValue()){
					return -1;
				}else{
					return e1.getTime().compareTo(e2.getTime());
				}
			}
		});
	}
	
	public List<EfLocationOrigin> getGpsLocation(Date beginDate,Date endDate,Long userId,Long deviceId) throws BizException{
		List<EfLocationGps> efLocationGpsList = efComLocationGpsServiceImpl.getByOpTag(0l,beginDate,endDate,userId,deviceId);
		List<EfLocationOrigin> list = new ArrayList<EfLocationOrigin>();
		for(EfLocationGps efLocationGps:efLocationGpsList){
			try{
				efLocationGps.setOpTag(1);
				EfLocationOrigin ejlLocation = new EfLocationOrigin();
				ejlLocation.setDeviceId(efLocationGps.getDeviceId());
				LatLng latLng = GpsLocationTransformGede.transformFromWGSToGCJ(new LatLng(Double.valueOf(efLocationGps.getLocation().split(",")[0]),Double.valueOf(efLocationGps.getLocation().split(",")[1])));
				ejlLocation.setLocation(latLng.latitude+","+latLng.longitude);
				ejlLocation.setRadius(new Long(new Random().nextInt(10)+20));
				ejlLocation.setUserId(efLocationGps.getUserId());
				ejlLocation.setTime(efLocationGps.getTime());
				ejlLocation.setSource(1);
				ejlLocation.setType(2);
				ejlLocation.setSourceId(efLocationGps.getId());
				ejlLocation.setStatus(1);//有效
				ejlLocation.setFlag(0);
				try {
					Regeocode regeocode = GaoDeLocationGetUtil.getCityNameByLocation(latLng.longitude+","+latLng.latitude);
					if(regeocode!=null){
						ejlLocation.setAddress(regeocode.getFormatted_address());
						ejlLocation.setRemark(regeocode.getFormatted_address());
						ejlLocation.setCity((regeocode.getAddressComponent().getCity()==null||regeocode.getAddressComponent().getCity()=="")?regeocode.getAddressComponent().getProvince():regeocode.getAddressComponent().getCity());
					}
				} catch (Exception e) {
					log.error("update gps location address", e);
				}
				list.add(ejlLocation);
			} catch (Exception e) {
				log.error("update gps location error", e);
			}
		}
		Context ctx = new Context();
		ctx.set("userId", 1);
		efComLocationGpsServiceImpl.save(ctx, efLocationGpsList);
		return list;
	}
	
	
	private List<EfLocationOrigin> getWiftLocation(String baseUrl,Date beginDate,Date endDate,Long userId,Long deviceId) throws BizException{
		List<EfLocationOrigin> resultList = new ArrayList<EfLocationOrigin>();
		List<EfLocationWifi>  efLocationWifiList =efLocationWifiServiceImpl.getByOpTag(0L,beginDate,endDate,userId,deviceId);
		if(efLocationWifiList == null){
			return resultList;
		}
		EjlDevice qrcode = null;
		for(EfLocationWifi efLocationWifi:efLocationWifiList){
			try{
				if(qrcode == null || qrcode.getId().longValue()!= efLocationWifi.getDeviceId()){
					qrcode = ejlComDeviceDaoImpl.getById(efLocationWifi.getDeviceId());
				}
				StringBuffer url = new StringBuffer();
				url.append(baseUrl);
				url.append("&accesstype=1");
				if(qrcode != null && qrcode.getCode()!=null)
				url.append("&imei="+qrcode.getCode());
				
				if(efLocationWifi.getMac1() != null){
					String mac = efLocationWifi.getMac1().replace("-",":");
					url.append("&macs="+mac+","+efLocationWifi.getSignal1()+","+efLocationWifi.getMacName1());
					if(efLocationWifi.getMac2()!= null){
						String mac2 = efLocationWifi.getMac2().replace("-",":");
						url.append("|"+mac2+","+efLocationWifi.getSignal2()+","+efLocationWifi.getMacName2());
					}
					if(efLocationWifi.getMac3()!= null){
						String mac3 = efLocationWifi.getMac3().replace("-",":");
						url.append("|"+mac3+","+efLocationWifi.getSignal3()+","+efLocationWifi.getMacName3());
					}
					if(efLocationWifi.getMac4()!= null){
						String mac4 = efLocationWifi.getMac4().replace("-",":");
						url.append("|"+mac4+","+efLocationWifi.getSignal4()+","+efLocationWifi.getMacName4());
					}
					if(efLocationWifi.getMac5()!= null){
						String mac5 = efLocationWifi.getMac5().replace("-",":");
						url.append("|"+mac5+","+efLocationWifi.getSignal5()+","+efLocationWifi.getMacName5());
					}
				}
				url.append("&output=json");
				GaoDeLocationStruc result = GaoDeLocationGetUtil.getGaoDeLocation(url.toString());
				if(result != null){
					//efLocationWifiServiceImpl.update(efLocationWifi);
					EfLocationOrigin ejlLocation = new EfLocationOrigin();
					ejlLocation.setGmtCreated(DateUtils.convertLong2Date(efLocationWifi.getTime()));
					ejlLocation.setDeviceId(efLocationWifi.getDeviceId());
					ejlLocation.setUserId(efLocationWifi.getUserId());
					ejlLocation.setTime(DateUtils.convertLong2Date(efLocationWifi.getTime()));
					ejlLocation.setSource(3);
					ejlLocation.setType(2);
					ejlLocation.setSourceId(efLocationWifi.getId());
					ejlLocation.setStatus(1);
					ejlLocation.setFlag(0);
					ejlLocation.setRadius(getRadius(null==result.getRadius()?0L:Long.valueOf(result.getRadius())));
					try{
					ejlLocation.setLocation(LocationUtil.getWeiduJinduConverter(result.getLocation()));
					}catch(Exception e){
						ejlLocation.setLocation("0,0");
						ejlLocation.setStatus(0);
						ejlLocation.setRemark("无效的wifi点");
					}
					try{
						ejlLocation.setCity(result.getCity());
						if(null!=result.getDesc()){
							String des = result.getDesc().replace(" " , "");
							ejlLocation.setRemark(des);
							ejlLocation.setAddress(des);
						}
					}catch(Exception e){
						log.error("get location error.result="+result.toString(), e);
					}
					//ejlComLocationServiceImpl.save(ejlLocation);
					resultList.add(ejlLocation);
				}else{
					efLocationWifi.setRemark("error:get result is null.");
				}
			}catch(Exception e){
				log.error("get location error", e);
				efLocationWifi.setRemark("error:"+e.getMessage());
			}
			efLocationWifi.setOpTag(1);
		}
		
		Context ctx = new Context();
		ctx.set("userId", -1);
		efLocationWifiServiceImpl.save(ctx, efLocationWifiList);
		return resultList;
	}
	
	
	public  List<EfLocationOrigin> getCellGbrsLocation(String baseUrl,Date beginDate,Date endDate,Long userId,Long deviceId) throws BizException{
		List<EfLocationOrigin> resultList = new ArrayList<EfLocationOrigin>();
		List<DeviceMobile> deviceMobileList = deviceMobileServiceImpl.getByOpTag(0L,beginDate,endDate,userId,deviceId);
		if(deviceMobileList == null){
			return resultList;
		}
		EjlDevice qrcode = null;
		for(DeviceMobile deviceMobile:deviceMobileList){
			try{
				if(qrcode == null || qrcode.getId().longValue()!= deviceMobile.getDeviceId()){
					qrcode = ejlComDeviceDaoImpl.getById(deviceMobile.getDeviceId());
				}
				StringBuffer url = new StringBuffer();
				url.append(baseUrl);
				url.append("&accesstype=0");
				if(qrcode != null && qrcode.getCode()!=null)
				url.append("&imei="+qrcode.getCode());
				url.append("&cdma=0");
				/*if(qrcode != null && qrcode.getImsi()!=null)
				url.append("&imsi="+qrcode.getImsi());*/
				url.append("&bts="+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac1()+","+deviceMobile.getCi1()+","+deviceMobile.getRssi1());
				if(deviceMobile.getLac2()!=null){
					url.append("&nearbts="+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac2()+","+deviceMobile.getCi2()+","+deviceMobile.getRssi2());
					if(deviceMobile.getLac3()!=null){
						url.append("|"+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac3()+","+deviceMobile.getCi3()+","+deviceMobile.getRssi3());
						if(deviceMobile.getLac4()!=null){
							url.append("|"+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac4()+","+deviceMobile.getCi4()+","+deviceMobile.getRssi4());
							if(deviceMobile.getLac5()!=null){
								url.append("|"+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac5()+","+deviceMobile.getCi5()+","+deviceMobile.getRssi5());
							}
						}
					}
				}
				url.append("&output=json");
				GaoDeLocationStruc result = GaoDeLocationGetUtil.getGaoDeLocation(url.toString());
				if(result != null){
					//deviceMobileServiceImpl.update(deviceMobile);
					EfLocationOrigin ejlLocation = new EfLocationOrigin();
					ejlLocation.setGmtCreated(DateUtils.convertLong2Date(deviceMobile.getTime()));
					ejlLocation.setDeviceId(deviceMobile.getDeviceId());
					//ejlLocation.setLocation(LocationUtil.getWaiduJinduConverter(result.getLocation()));
					ejlLocation.setTime(DateUtils.convertLong2Date(deviceMobile.getTime()));
					ejlLocation.setUserId(deviceMobile.getUserId());
					ejlLocation.setSource(2);
					ejlLocation.setType(2);
					ejlLocation.setSourceId(deviceMobile.getId());
					ejlLocation.setStatus(1);
					ejlLocation.setFlag(0);
					ejlLocation.setRadius(getRadius(null==result.getRadius()?0L:Long.valueOf(result.getRadius())));
					try{
						ejlLocation.setLocation(LocationUtil.getWeiduJinduConverter(result.getLocation()));
					}catch(Exception e){
						ejlLocation.setLocation("0,0");
						ejlLocation.setStatus(0);
						ejlLocation.setRemark("无效的gbrs点");
					}
					try{
						ejlLocation.setCity(result.getCity());
						if(null!=result.getDesc()){
							String des = result.getDesc().replace(" " , "");
							ejlLocation.setAddress(des);
							ejlLocation.setRemark(des);
							ejlLocation.setAddress(des);
						}
					}catch(Exception e){
						log.error("get location error.result="+result.toString(), e);
					}
					//ejlComLocationServiceImpl.save(ejlLocation);
					resultList.add(ejlLocation);
					
				}else{
					deviceMobile.setRemark("error:get result is null.");
				}
			}catch(Exception e){
				log.error("get location error", e);
				deviceMobile.setRemark("error:"+e.getMessage());
			}
			deviceMobile.setOpTag(1);
		}
		Context ctx = new Context();
		ctx.set("userId", -1);
		deviceMobileServiceImpl.save(ctx, deviceMobileList);
		return resultList;
	}
	
	private Long getRadius(Long radius){
		if(null==radius || radius.longValue()<0 || radius.longValue()>550){
			radius=new Long(new Random().nextInt(10)+500);
		}
		return radius;
	}
	private void handlerLocationOrigin(final Long userId, final Long deviceId,final Date timeFrom, final Date timeTo,ExecutorService th) throws Exception{
		final String baseUrl = "http://apilocate.amap.com/position?key="+key+"&";
		//获取用户设备查询的时间段
		final List<List<Date>> dateSplitList = LocationUtil.dateSplit(timeFrom, timeTo, EACH_PRO_MINUTES);

		final String target=userId+Separator.vertical+deviceId;
		new BizMultiThread(th,Constant.LOCATION_ORIGIN_LOCK+target,60*30) {
			@Override
			protected void doBiz() throws BizException {
				for(List<Date> dateList : dateSplitList){
					List<EfLocationOrigin> list = new ArrayList<EfLocationOrigin>();
					list.addAll(getCellGbrsLocation(baseUrl,dateList.get(0),dateList.get(1),userId,deviceId));
					list.addAll(getGpsLocation(dateList.get(0),dateList.get(1),userId,deviceId));
					list.addAll(getWiftLocation(baseUrl,dateList.get(0),dateList.get(1),userId,deviceId));
					if(!list.isEmpty()){
						locationListSort(list);
						Context ctx = new Context();
						ctx.set("userId", -1);
						efComLocationOriginServiceImpl.save(ctx, list);//(ctx, list);
					}
				}
			}
		}.start();
	}

	@Resource(name = "RedisClient")
	private RedisClient redisClient;

	@Override
	public void initLocationOrigin(final Long userId, final Long deviceId) throws Exception {
		final int days=1;	//1天之内 根据实际有意义的轨迹要求(24小时)++天数
		final Date timeTo=DateUtils.addMinutes(DateUtils.currentDate(),-1);
		final Date timeFrom=DateUtils.addDays(timeTo, -1*days);
		final String baseUrl = "http://apilocate.amap.com/position?key="+key+"&";
		//获取用户设备查询的时间段
		final List<List<Date>> dateSplitList = LocationUtil.dateSplit(timeFrom, timeTo, EACH_PRO_MINUTES);

		final String target=userId+Separator.vertical+deviceId;
		String lockKey = Constant.LOCATION_ORIGIN_LOCK+target;
		if(!redisClient.lock(lockKey, 60*30)){
			return;	//拿锁失败 说明前一个任务执行中 则本次不执行
		}
		List<EfLocationOrigin> listAll = new ArrayList<EfLocationOrigin>();
		try{
		for(List<Date> dateList : dateSplitList){
			List<EfLocationOrigin> list = new ArrayList<EfLocationOrigin>();
			list.addAll(getCellGbrsLocation(baseUrl,dateList.get(0),dateList.get(1),userId,deviceId));
			list.addAll(getGpsLocation(dateList.get(0),dateList.get(1),userId,deviceId));
			list.addAll(getWiftLocation(baseUrl,dateList.get(0),dateList.get(1),userId,deviceId));
			if(!list.isEmpty()){
				locationListSort(list);
				Context ctx = new Context();
				ctx.set("userId", -1);
				efComLocationOriginServiceImpl.save(ctx, list);
				listAll.addAll(list);
			}
		}}catch(Exception e){
			log.error(" user query location initLocationOrigin error", e);
			throw e;
		}finally{
			redisClient.unlock(lockKey);
		}
		
		if(!listAll.isEmpty()){
			tskLocationSemiServiceNew.doProcessOrigin(userId, deviceId);
		}
	}
}
