package com.winterframework.efamily.institution.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEjlComHealthHeartRateDao;
import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.entity.DeviceAlarmLastForPlatform;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.OrgUser;
import com.winterframework.efamily.entity.UserHeartRateAndBloodPressure;
import com.winterframework.efamily.institution.dto.BloodPressureInfo;
import com.winterframework.efamily.institution.dto.BloodSugarInfo;
import com.winterframework.efamily.institution.dto.DeviceAlarmLastInfo;
import com.winterframework.efamily.institution.dto.HealthExceptionInfo;
import com.winterframework.efamily.institution.dto.HeartRateInfo;
import com.winterframework.efamily.institution.dto.LocationBaseInfo;
import com.winterframework.efamily.institution.dto.UserLocationInfo;
import com.winterframework.efamily.institution.service.IMainPageManageService;
import com.winterframework.efamily.institution.util.DataHandlerUtil;
import com.winterframework.efamily.institution.web.controller.CustomerAlarmReceiveController;
import com.winterframework.efamily.service.IComEjlHealthBloodPressureService;
import com.winterframework.efamily.service.IEfComLocationSemiService;
import com.winterframework.efamily.service.IEjlComHealthHeartRateService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.IOrgComUserService;
import com.winterframework.modules.spring.exetend.PropertyConfig;


@Service("mainPageManageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class MainPageManageServiceImpl implements IMainPageManageService{

	@Resource(name = "RedisClient")
	private RedisClient redisClient; 

	@Resource(name="orgComUserServiceImpl")
	private IOrgComUserService orgComUserServiceImpl;
	
	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserServiceImpl;

	@Resource(name="efComLocationSemiServiceImpl")
	private IEfComLocationSemiService efComLocationSemiServiceImpl;

	@Resource(name="ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;

	@Resource(name="ejlComLocationDaoImpl")
	private IEjlComLocationDao ejlComLocationDaoImpl;
	
	@Resource(name="ejlComHealthHeartRateServiceImpl")
	private IEjlComHealthHeartRateService ejlComHealthHeartRateServiceImpl;	
	
	@Resource(name = "ejlComHealthBloodPressureServiceImpl")
	private IComEjlHealthBloodPressureService ejlComHealthBloodPressureServiceImpl;
	
	private Logger log = LoggerFactory.getLogger(MainPageManageServiceImpl.class);
	
	
	public List<UserHeartRateAndBloodPressure> getMainPageData(Long orgId,String queryValue){
		return getMainPageData(orgId, 1,queryValue);//***  status: 0 未绑定 1 绑定  9 删除
	}
	
	public List<UserHeartRateAndBloodPressure> getMainPageData(Long orgId,Integer status,String queryValue){
		String name = null;
		String phoneNumber = null;
		if(StringUtils.isNotBlank(queryValue)){
/*			if(DataHandlerUtil.isNumeric(queryValue)){
				phoneNumber = queryValue;
			}else{
			name = queryValue;
			}*/
			name = queryValue.trim();
		}
		
		List<UserHeartRateAndBloodPressure> userHeartRateAndBloodPressureList = orgComUserServiceImpl.getUserHeartRateAndBloodPressureList(orgId, status, name, phoneNumber);
		if(userHeartRateAndBloodPressureList==null ){
			userHeartRateAndBloodPressureList = new ArrayList<UserHeartRateAndBloodPressure>();
		}else{
			for(UserHeartRateAndBloodPressure userHeartRateAndBloodPressure : userHeartRateAndBloodPressureList){
				String location = getLocation(userHeartRateAndBloodPressure.getUserId());
				userHeartRateAndBloodPressure.setLocation(location);
			}
		}
        
		return userHeartRateAndBloodPressureList;
	}
	
	public String getLocation(Long userId) {
	    String location = "";
	    try{
		    EjlUserDevice userDevice =  ejlComUserDeviceDaoImpl.getOnlyDeviceByUser(userId);
		    if(userDevice == null){
		    	return "";
		    	//throw new BizException();
		    }
			EfLocationSemi efLocationSemi = efComLocationSemiServiceImpl.getLastLocation(userId,userDevice.getDeviceId());
			if(efLocationSemi!=null){
				location = efLocationSemi.getLocation();	
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    	log.error("根据用户ID获取最后一条定位数据 message : "+e.getMessage());
	    }
		return location;
   }
	
	public UserLocationInfo getLastLocation(Long userId) throws Exception {
		    UserLocationInfo info=new UserLocationInfo();
		    EjlUserDevice userDevice =  ejlComUserDeviceDaoImpl.getOnlyDeviceByUser(userId);
		    if(userDevice == null){
		    	throw new BizException();
		    }
			EfLocationSemi efLocationSemi = efComLocationSemiServiceImpl.getLastLocation(userId,userDevice.getDeviceId());
			if(efLocationSemi!=null){
				EjlUser user = ejlComUserServiceImpl.get(userId);
				info.setUserId(userId);
				info.setUserName(user.getNickName());
				info.setHeadImg(user.getHeadImg());
				info.setLocation(efLocationSemi.getLocation());
				info.setType(efLocationSemi.getType());
				info.setTime(efLocationSemi.getTimeEnd().getTime());
				info.setAddressDesc(efLocationSemi.getAddress());
				info.setDuration(DateUtils.calcMinutesBetween(efLocationSemi.getTimeBegin(), efLocationSemi.getTimeEnd()));
			}
			
			return info;
    }
	
	public List<LocationBaseInfo> getLocationList(Long userId,Integer hourRecent) throws Exception {
		 List<LocationBaseInfo> locationBaseInfoList = new ArrayList<LocationBaseInfo>();
	    EjlUserDevice userDevice =  ejlComUserDeviceDaoImpl.getOnlyDeviceByUser(userId);
	    if(userDevice == null){
	    	throw new BizException();
	    }
		Date time=DateUtils.addHours(new Date(), -hourRecent);
		List<EjlLocation> ejlLocations=ejlComLocationDaoImpl.getUserLocation(userId, userDevice.getDeviceId(),time);
		if(!ejlLocations.isEmpty()){
			EjlLocation locationBefore = null;
			for(int i=0;i<ejlLocations.size();i++){
				EjlLocation lo = ejlLocations.get(i);
				LocationBaseInfo struc=new LocationBaseInfo();
				struc.setLocation(lo.getLocation());
				struc.setType(lo.getType());
				struc.setTime(DateUtils.addSeconds(lo.getTime(),lo.getTimeStay()).getTime());
				struc.setAddressDesc(lo.getAddress());
				struc.setDuration(Long.valueOf(lo.getTimeStay()/60));
				if(locationBefore!=null){
					if(locationBefore.getTime().getTime()/60 == lo.getTime().getTime()/60){
						continue;
					}
				}
				locationBaseInfoList.add(struc);
				locationBefore = lo;
			}
		}
		
		return locationBaseInfoList;
    }
	

	
	public List<DeviceAlarmLastInfo> getDeviceAlarmExceptionData(Long orgId,Long time) throws Exception{
		Map<Long,DeviceAlarmLastInfo> deviceAlarmLastInfoMap = new HashMap<Long,DeviceAlarmLastInfo>();
		List<DeviceAlarmLastForPlatform> deviceAlarmLastForPlatformList =  orgComUserServiceImpl.getDeviceAlarmLastForPlatformList(orgId, 1,time);
		for(DeviceAlarmLastForPlatform deviceAlarmLastForPlatformTemp:deviceAlarmLastForPlatformList){
			DeviceAlarmLastInfo deviceAlarmLastInfo = deviceAlarmLastInfoMap.get(deviceAlarmLastForPlatformTemp.getUserId());
			if(deviceAlarmLastInfo == null){
				deviceAlarmLastInfo = new DeviceAlarmLastInfo();
				
				if( deviceAlarmLastForPlatformTemp.getType().longValue() != EfDeviceAlarm.Type.HEART.getValue()
				  &&deviceAlarmLastForPlatformTemp.getType().longValue() != EfDeviceAlarm.Type.BLOOD.getValue()
				  &&deviceAlarmLastForPlatformTemp.getType().longValue() != EfDeviceAlarm.Type.SOS.getValue()
				  &&deviceAlarmLastForPlatformTemp.getType().longValue() != EfDeviceAlarm.Type.FENCE.getValue()
				  &&deviceAlarmLastForPlatformTemp.getType().longValue() != EfDeviceAlarm.Type.BLOODSUGAR.getValue()){
					//*** 只处理这几种
					continue;
				}
				
			    EjlUserDevice userDevice =  ejlComUserDeviceDaoImpl.getOnlyDeviceByUser(deviceAlarmLastForPlatformTemp.getUserId());
			    if(userDevice == null){
			    	log.error("获取主页异常数据时：根据用户ID获取 userDevice 为空 .");
			    	continue;
			    }
				EfLocationSemi efLocationSemi = efComLocationSemiServiceImpl.getLastLocation(deviceAlarmLastForPlatformTemp.getUserId(),userDevice.getDeviceId());
				if(efLocationSemi==null){
					log.error("获取主页异常数据时：根据用户ID和设备ID获取 efLocationSemi 为空 .");
			    	continue;
				}
				OrgUser orgUser = orgComUserServiceImpl.getOrgUserByUserId(deviceAlarmLastForPlatformTemp.getUserId());
				deviceAlarmLastInfo.setOrgUserId(orgUser.getId());
				deviceAlarmLastInfo.setLocation(efLocationSemi.getLocation());
				deviceAlarmLastInfo.setUserId(deviceAlarmLastForPlatformTemp.getUserId()); 
				deviceAlarmLastInfo.setHeadImg(deviceAlarmLastForPlatformTemp.getHeadImg());
				deviceAlarmLastInfo.setUserName(deviceAlarmLastForPlatformTemp.getUserName());
				deviceAlarmLastInfo.getHealthExceptionInfo().add( deviceAlarmLastForPlatformTemp.getValue().replace(",", "#"));
				//*** 更好的组装异常数据
				//deviceAlarmLastInfo.setValue("type="+deviceAlarmLastForPlatformTemp.getType()+"&"+deviceAlarmLastForPlatformTemp.getValue());
				//HealthExceptionInfo healthExceptionInfo = new HealthExceptionInfo();
				//Long alarmType = deviceAlarmLastForPlatformTemp.getType();
				//healthExceptionInfo.setAlarmType(alarmType);
				
/*				Map<String,String> map = new Gson().fromJson(deviceAlarmLastForPlatformTemp.getValue(), Map.class);
				map.put("alarmType", deviceAlarmLastForPlatformTemp.getType()+"");*/
				
                /*
                if(alarmType.longValue() == EfDeviceAlarm.Type.HEART.getValue()){
					healthExceptionInfo.setFlag(Integer.valueOf(map.get("type")));//1 低  2 高
					healthExceptionInfo.setValue(map.get("heartRate"));
				}else if(alarmType.longValue() == EfDeviceAlarm.Type.BLOOD.getValue()){
					healthExceptionInfo.setFlag(Integer.valueOf(map.get("type")));//1 低  2 高
					healthExceptionInfo.setValue(map.get("bloodPressure"));
				}else if(alarmType.longValue() == EfDeviceAlarm.Type.BLOODSUGAR.getValue()){
					healthExceptionInfo.setFlag(Integer.valueOf(map.get("type")));//1 低  2 高
					healthExceptionInfo.setValue(map.get("bloodSugar"));
				}else{
					//*** 其他类型暂时不做处理  ****
					continue;
				}*/
				
				
				deviceAlarmLastInfoMap.put(deviceAlarmLastForPlatformTemp.getUserId(),deviceAlarmLastInfo );
				
				continue;
			}
			//deviceAlarmLastInfo.setValue(deviceAlarmLastInfo.getValue()+"#"+"type="+deviceAlarmLastForPlatformTemp.getType()+"&"+deviceAlarmLastForPlatformTemp.getValue());
			deviceAlarmLastInfo.getHealthExceptionInfo().add(deviceAlarmLastForPlatformTemp.getValue().replace(",", "#"));
		}
		
		List<DeviceAlarmLastInfo> mapValuesList = new ArrayList<DeviceAlarmLastInfo>(deviceAlarmLastInfoMap.values());  
		
		return mapValuesList;
	}
	
    public Integer getRegionValue(Long value,Long high,Long low){
    	Integer regionValue = 0;
    	if(value<low){
    		regionValue = 1;
    	}
    	if(value>high){
    		regionValue = 2;
    	}
    	return regionValue;
    }
    
    public Integer getRegionValue(Integer value,Integer high,Integer low){
    	Integer regionValue = 0;
    	if(value<low){
    		regionValue = 1;
    	}
    	if(value>high){
    		regionValue = 2;
    	}
    	return regionValue;
    }
	
	public Map<String,Object> getUserHealthInfo(Long userId,String startDate,String endDate){
		
		if(StringUtils.isBlank(startDate)){
			startDate = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_PATTERN);
		}
		
		if(StringUtils.isBlank(endDate)){
			endDate = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_PATTERN);
		}
		
		Long fromTime = DateUtils.getTimeLong(startDate+" 00:00:00");
		Long endTime = DateUtils.getTimeLong(endDate+" 23:59:59");
		
		Long healthHeartLt = 60L;// 心率 小
		Long healthHeartGt =100L;// 心率 大
		
		Integer systolicPressureLt = 90;// 收缩压 小
		Integer systolicPressureGt = 139;// 收缩压 大
		
		Integer diastolicPressureLt = 60;// 舒张压 小
		Integer diastolicPressureGt = 89;// 舒张压 大
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//******************* 心率  ***********************
		List<HeartRateInfo> heartRateInfoList = new ArrayList<HeartRateInfo>();
		List<EjlHealthHeartRate> ejlHealthHeartRateList = ejlComHealthHeartRateServiceImpl.getUserHeartRateByTime(userId, fromTime, endTime);
		if(ejlHealthHeartRateList!=null && ejlHealthHeartRateList.size()>0){
			for(EjlHealthHeartRate ejlHealthHeartRate : ejlHealthHeartRateList){
				HeartRateInfo heartRateInfo = new HeartRateInfo();
				//心率 0 正常 1低  2高 
				heartRateInfo.setFlag(getRegionValue(ejlHealthHeartRate.getRate(),healthHeartGt,healthHeartLt));
				heartRateInfo.setValue(ejlHealthHeartRate.getRate());
				heartRateInfo.setFromTime(ejlHealthHeartRate.getFromTime());
				heartRateInfo.setToTime(ejlHealthHeartRate.getToTime());
				heartRateInfoList.add(heartRateInfo);
			}
		}
		resultMap.put("heartRateInfo", heartRateInfoList);
		
		//******************* 血压  ***********************
		List<BloodPressureInfo> bloodPressureInfoList = new ArrayList<BloodPressureInfo>();
		List<EjlHealthBloodPressure> ejlHealthBloodPressureList = ejlComHealthBloodPressureServiceImpl.getBloodPressureByTime(userId, fromTime, endTime);
		if(ejlHealthBloodPressureList!=null && ejlHealthBloodPressureList.size()>0){
			for(EjlHealthBloodPressure ejlHealthBloodPressure : ejlHealthBloodPressureList){
				BloodPressureInfo bloodPressureInfo = new BloodPressureInfo();
				//舒张压 0 正常 1低  2高
				bloodPressureInfo.setDiastolicFlag(getRegionValue(ejlHealthBloodPressure.getHigh(),systolicPressureLt,systolicPressureGt));
				bloodPressureInfo.setDiastolicPressure(ejlHealthBloodPressure.getHigh());
				//收缩压  0 正常 1低  2高
				bloodPressureInfo.setSystolicFlag(getRegionValue(ejlHealthBloodPressure.getLow(),diastolicPressureLt,diastolicPressureGt));
				bloodPressureInfo.setSystolicPressure(ejlHealthBloodPressure.getLow());
				bloodPressureInfo.setFromTime(ejlHealthBloodPressure.getFromTime());
				bloodPressureInfo.setToTime(ejlHealthBloodPressure.getToTime());
				bloodPressureInfoList.add(bloodPressureInfo);
			}
		}
		resultMap.put("bloodPressureInfo", bloodPressureInfoList);
		
		
		//******************* 血糖  （血糖还没有  先写个默认值）***********************
		List<BloodSugarInfo> bloodSugarInfoList = new ArrayList<BloodSugarInfo>();
		BloodSugarInfo bloodSugarInfo = new BloodSugarInfo();
		bloodSugarInfo.setValue(50L);
		bloodSugarInfo.setFlag(1);
		bloodSugarInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 11:00:00"));
		bloodSugarInfo.setToTime(DateUtils.getTimeLong("2016-09-27 11:05:00"));
		bloodSugarInfoList.add(bloodSugarInfo);
		
		bloodSugarInfo = new BloodSugarInfo();
		bloodSugarInfo.setValue(55L);
		bloodSugarInfo.setFlag(2);
		bloodSugarInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 10:00:00"));
		bloodSugarInfo.setToTime(DateUtils.getTimeLong("2016-09-27 10:05:00"));
		bloodSugarInfoList.add(bloodSugarInfo);
		
		bloodSugarInfo = new BloodSugarInfo();
		bloodSugarInfo.setValue(59L);
		bloodSugarInfo.setFlag(2);
		bloodSugarInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 12:00:00"));
		bloodSugarInfo.setToTime(DateUtils.getTimeLong("2016-09-27 12:05:00"));
		bloodSugarInfoList.add(bloodSugarInfo);
		resultMap.put("bloodSugarInfo", bloodSugarInfoList);
		
		return resultMap;
	}
	
	
	public static void main(String[] args){
		
		Map<String,String> map = new Gson().fromJson("{\"location\":\"116.294176,39.958508\",\"radius\":\"528\",\"type\":\"2\"}", Map.class);
		System.out.println("map = "+map.get("location"));
		
		/*Map<String,Object> resultMap = new HashMap<String,Object>();
		//*******************  ***********************
		List<HeartRateInfo> heartRateInfoList = new ArrayList<HeartRateInfo>();
		HeartRateInfo heartRateInfo = new HeartRateInfo();
		heartRateInfo.setValue(80L);
		heartRateInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 11:00:00"));
		heartRateInfo.setToTime(DateUtils.getTimeLong("2016-09-27 11:05:00"));
		heartRateInfo.setFlag(2);
		heartRateInfoList.add(heartRateInfo);
		
		heartRateInfo = new HeartRateInfo();
		heartRateInfo.setValue(70L);
		heartRateInfo.setFlag(1);
		heartRateInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 10:00:00"));
		heartRateInfo.setToTime(DateUtils.getTimeLong("2016-09-27 10:05:00"));
		heartRateInfoList.add(heartRateInfo);
		
		heartRateInfo = new HeartRateInfo();
		heartRateInfo.setValue(60L);
		heartRateInfo.setFlag(1);
		heartRateInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 12:00:00"));
		heartRateInfo.setToTime(DateUtils.getTimeLong("2016-09-27 12:05:00"));
		heartRateInfoList.add(heartRateInfo);
		resultMap.put("heartRateInfo", heartRateInfoList);
		
		//******************* ***********************
		List<BloodPressureInfo> bloodPressureInfoList = new ArrayList<BloodPressureInfo>();
		BloodPressureInfo bloodPressureInfo = new BloodPressureInfo();
		bloodPressureInfo.setValue(80L);
		bloodPressureInfo.setFlag(1);
		bloodPressureInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 11:00:00"));
		bloodPressureInfo.setToTime(DateUtils.getTimeLong("2016-09-27 11:05:00"));
		
		bloodPressureInfo = new BloodPressureInfo();
		bloodPressureInfo.setValue(85L);
		bloodPressureInfo.setFlag(2);
		bloodPressureInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 10:00:00"));
		bloodPressureInfo.setToTime(DateUtils.getTimeLong("2016-09-27 10:05:00"));
		
		bloodPressureInfo = new BloodPressureInfo();
		bloodPressureInfo.setValue(99L);
		bloodPressureInfo.setFlag(2);
		bloodPressureInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 12:00:00"));
		bloodPressureInfo.setToTime(DateUtils.getTimeLong("2016-09-27 12:05:00"));
		
		bloodPressureInfoList.add(bloodPressureInfo);
		resultMap.put("bloodPressureInfo", bloodPressureInfoList);
		
		//******************************************
		List<BloodSugarInfo> bloodSugarInfoList = new ArrayList<BloodSugarInfo>();
		BloodSugarInfo bloodSugarInfo = new BloodSugarInfo();
		bloodSugarInfo.setValue(50L);
		bloodSugarInfo.setFlag(1);
		bloodSugarInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 11:00:00"));
		bloodSugarInfo.setToTime(DateUtils.getTimeLong("2016-09-27 11:05:00"));
		bloodSugarInfoList.add(bloodSugarInfo);
		
		bloodSugarInfo = new BloodSugarInfo();
		bloodSugarInfo.setValue(55L);
		bloodSugarInfo.setFlag(2);
		bloodSugarInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 10:00:00"));
		bloodSugarInfo.setToTime(DateUtils.getTimeLong("2016-09-27 10:05:00"));
		bloodSugarInfoList.add(bloodSugarInfo);
		
		bloodSugarInfo = new BloodSugarInfo();
		bloodSugarInfo.setValue(59L);
		bloodSugarInfo.setFlag(2);
		bloodSugarInfo.setFromTime(DateUtils.getTimeLong("2016-09-27 12:00:00"));
		bloodSugarInfo.setToTime(DateUtils.getTimeLong("2016-09-27 12:05:00"));
		bloodSugarInfoList.add(bloodSugarInfo);
		resultMap.put("bloodSugarInfo", bloodSugarInfoList);
		
		System.out.println(JsonUtils.toJson(resultMap));*/
	}
	
	
	
	/*	public static void main(String[] args){
	Map<Long,DeviceAlarmLastInfo> deviceAlarmLastInfoMap = new HashMap<Long,DeviceAlarmLastInfo>();
	DeviceAlarmLastInfo deviceAlarmLastInfo = new DeviceAlarmLastInfo();
	deviceAlarmLastInfo.setUserId(10L);
	deviceAlarmLastInfo.setHeadImg("headimg01");
	deviceAlarmLastInfo.setUserName("username01");
	deviceAlarmLastInfo.setValue("value01");
	deviceAlarmLastInfoMap.put(10L,deviceAlarmLastInfo );
	
	deviceAlarmLastInfo = new DeviceAlarmLastInfo();
	deviceAlarmLastInfo.setUserId(20L);
	deviceAlarmLastInfo.setHeadImg("headimg02");
	deviceAlarmLastInfo.setUserName("username02");
	deviceAlarmLastInfo.setValue("value02");
	deviceAlarmLastInfoMap.put(20L,deviceAlarmLastInfo );
	
	deviceAlarmLastInfo = new DeviceAlarmLastInfo();
	deviceAlarmLastInfo.setUserId(30L);
	deviceAlarmLastInfo.setHeadImg("headimg03");
	deviceAlarmLastInfo.setUserName("username03");
	deviceAlarmLastInfo.setValue("value03");
	deviceAlarmLastInfoMap.put(30L,deviceAlarmLastInfo );
	
	deviceAlarmLastInfo = new DeviceAlarmLastInfo();
	deviceAlarmLastInfo.setUserId(40L);
	deviceAlarmLastInfo.setHeadImg("headimg04");
	deviceAlarmLastInfo.setUserName("username04");
	deviceAlarmLastInfo.setValue("value04");
	deviceAlarmLastInfoMap.put(40L,deviceAlarmLastInfo );
	
	List<DeviceAlarmLastInfo> mapValuesList = new ArrayList<DeviceAlarmLastInfo>(deviceAlarmLastInfoMap.values());  
	System.out.println(JsonUtils.toJson(mapValuesList));
	
	System.out.println(JsonUtils.toJson(deviceAlarmLastInfoMap.entrySet()));
}*/
	/*      
	 * 		 
	        Map<Long,MainPageMemberHealthInfo> mainPageMemberHealthInfoMap = new HashMap<Long,MainPageMemberHealthInfo>();
			Set<String> bloodPressureSet = redisClient.keys("");
			  if(bloodPressureSet == null || bloodPressureSet.size()<=0){  
	            
	        }  
	        String bloodPressureArr[] = new String[bloodPressureSet.size()];  
	        int i =0;   
			String isException = "1";
			
	         for (String key : bloodPressureSet) {  
	        	String value =  redisClient.get(key);
	        	if(isException.equals(value)){
	        		String[] valueArr = value.split("_");
	        		MainPageMemberHealthInfo 
	        	}
	        }  
	 */
	
	
}
