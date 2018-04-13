package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfPlatformDeviceVersion;

public interface IEfPlatformDeviceVersionDao  extends IBaseDao<EfPlatformDeviceVersion>{
	EfPlatformDeviceVersion getLatestByDeviceTypeAndDeviceModelAndCustomer(Integer deviceType,String deviceModel,Integer customerNumber) throws Exception;
	
	EfPlatformDeviceVersion getByDeviceTypeAndDeviceModelAndDeviceVersion(Integer deviceType,String deviceModel,String deviceVersion) throws Exception;
}
