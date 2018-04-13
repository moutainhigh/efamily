package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfPlatformDeviceSetting;

public interface IEfPlatformDeviceSettingService extends IBaseService<EfPlatformDeviceSetting> { 
	EfPlatformDeviceSetting getByDeviceType(Integer deviceType) throws BizException;
}
