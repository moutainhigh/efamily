package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfDeviceAlarmLast;

public interface IEfDeviceAlarmLastService extends IBaseService<EfDeviceAlarmLast> { 
	EfDeviceAlarmLast getByUserIdAndDeviceId(Long userId,Long deviceId) throws BizException;
	
	EfDeviceAlarmLast getByUserIdAndType(Long userId,Integer type)throws BizException;
	
	public int closeUserExceptionRemind(Long userId) throws BizException;
}
