package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;

public interface IEfDeviceAlarmDao  extends IBaseDao<EfDeviceAlarm>{ 
	
	List<EfDeviceAlarm> getByUserIdAndDeviceId(Long userId,Long deviceId) throws Exception;
	List<EfDeviceAlarm> getByUserIdAndDeviceIdAndType(Long userId,Long deviceId,Integer type) throws Exception;
	List<EfDeviceAlarm> getByUserIdAndDeviceIdAndTypeAndTime(Long userId,Long deviceId,Integer type,Long time) throws Exception;
	
}
