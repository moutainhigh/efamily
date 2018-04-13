package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfDeviceAlarm;

public interface IEfDeviceAlarmService extends IBaseService<EfDeviceAlarm> { 
	List<EfDeviceAlarm> getByUserIdAndDeviceId(Long userId,Long deviceId) throws BizException;
	List<EfDeviceAlarm> getByUserIdAndDeviceIdAndType(Long userId,Long deviceId,Integer type) throws BizException;
	List<EfDeviceAlarm> getByUserIdAndDeviceIdAndTypeAndTime(Long userId,Long deviceId,Integer type,Long time) throws BizException;
}
