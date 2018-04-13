package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfPlatformDeviceVersion;

public interface IEfPlatformDeviceVersionService extends IBaseService<EfPlatformDeviceVersion> { 
	EfPlatformDeviceVersion getLatestByDeviceTypeAndDeviceModelAndCustomer(Integer deviceType,String deviceModel,Integer customerNumber) throws BizException;
	
	EfPlatformDeviceVersion getByDeviceTypeAndDeviceModelAndDeviceVersion(Integer deviceType,String deviceModel,String deviceVersion) throws BizException;
}
