package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfDeviceOperation;

public interface IEfDeviceOperationService extends IBaseService<EfDeviceOperation> { 
	EfDeviceOperation getByUserIdAndDeviceId(Long userId,Long deviceId) throws BizException;
	EfDeviceOperation getByUserIdAndDeviceIdAndCodeAndStatusAndTime(Long userId,Long deviceId,Integer type,Integer status,Long time) throws BizException;
	/**
	 * @param userId
	 * @param deviceId
	 * @param type
	 * @param time 时间仅用来过滤数据
	 * @return
	 * @throws BizException
	 */
	EfDeviceOperation getLastOneByUserIdAndDeviceIdAndCodeAndTime(Long userId,Long deviceId,Integer code,Long time) throws BizException;
	
}
