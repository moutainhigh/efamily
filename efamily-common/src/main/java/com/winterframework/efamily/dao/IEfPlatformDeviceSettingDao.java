package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfPlatformDeviceSetting;

public interface IEfPlatformDeviceSettingDao  extends IBaseDao<EfPlatformDeviceSetting>{
	EfPlatformDeviceSetting getByDeviceType(Integer deviceType) throws Exception;
}
