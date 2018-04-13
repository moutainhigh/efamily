package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfDeviceAlarmLast;

public interface IEfDeviceAlarmLastDao  extends IBaseDao<EfDeviceAlarmLast>{ 
	
	EfDeviceAlarmLast getByUserIdAndDeviceId(Long userId,Long deviceId) throws Exception;
	
	public EfDeviceAlarmLast getByUserIdAndType(Long userId,Integer type) throws Exception;
	
	public int closeUserExceptionRemind(Long userId) throws Exception;
}
