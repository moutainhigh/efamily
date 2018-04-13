package com.winterframework.efamily.institution.service;

import java.util.List;
import java.util.Map;

import com.winterframework.efamily.entity.UserHeartRateAndBloodPressure;
import com.winterframework.efamily.institution.dto.DeviceAlarmLastInfo;
import com.winterframework.efamily.institution.dto.LocationBaseInfo;
import com.winterframework.efamily.institution.dto.UserLocationInfo;

public interface IMainPageManageService {

	public List<UserHeartRateAndBloodPressure> getMainPageData(Long orgId,String queryValue);
	
	public List<UserHeartRateAndBloodPressure> getMainPageData(Long orgId,Integer status,String queryValue);
	
	public UserLocationInfo getLastLocation(Long userId) throws Exception;
	
	public List<LocationBaseInfo> getLocationList(Long userId,Integer hourRecent) throws Exception ;
	
	public List<DeviceAlarmLastInfo> getDeviceAlarmExceptionData(Long orgId,Long time) throws Exception;
	
	public Map<String,Object> getUserHealthInfo(Long userId,String startDate,String endDate);
	
}
