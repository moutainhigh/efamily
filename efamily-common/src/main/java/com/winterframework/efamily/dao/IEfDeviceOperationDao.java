package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfDeviceOperation;

public interface IEfDeviceOperationDao  extends IBaseDao<EfDeviceOperation>{ 
	
	EfDeviceOperation getByUserIdAndDeviceId(Long userId,Long deviceId) throws Exception;
	EfDeviceOperation getByUserIdAndDeviceIdAndCodeAndStatusAndTime(Long userId,Long deviceId,Integer code,Integer status,Long time) throws Exception;
	EfDeviceOperation getLastOneByUserIdAndDeviceIdAndCodeAndTime(Long userId,Long deviceId,Integer code,Long time) throws Exception;
}
