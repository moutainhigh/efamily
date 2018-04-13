package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.DeviceAlarmLastForPlatform;
import com.winterframework.efamily.entity.MemberSimpleInfoStruct;
import com.winterframework.efamily.entity.OrgBindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindUserBaseInfo;
import com.winterframework.efamily.entity.OrgUser;
import com.winterframework.efamily.entity.UserHeartRateAndBloodPressure;
import com.winterframework.efamily.entity.WatchParamBaseInfo;

public interface IOrgComUserDao   extends IBaseDao<OrgUser>{ 

	public List<UserHeartRateAndBloodPressure> getUserHeartRateAndBloodPressureList(Long orgId,Integer status,String name,String phoneNumber);
	
	public List<DeviceAlarmLastForPlatform> getDeviceAlarmLastForPlatformList(Long orgId,Integer status,Long time) ;
	
	public List<MemberSimpleInfoStruct> getMemberSimpleInfoStructList(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber,String idNumber);
	
	public List<OrgBindDeviceBaseInfo> getOrgBindDeviceBaseInfo(Long orgId,Integer start,Integer pagesize,String orgUserName,String imei);
	
	public List<OrgUnbindUserBaseInfo> getOrgUnbindUserBaseInfo(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber);
	
	public WatchParamBaseInfo getWatchParamBaseInfo(Long orgUserId);
	
}
