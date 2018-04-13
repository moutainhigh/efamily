package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;

public interface IEjlComDeviceConfigService extends IBaseService<EjlDeviceParmConfig>{
	EjlDeviceParmConfig getByDeviceIdAndKey(Long deviceId,String key) throws BizException;
	public int updateByDeviceAndKey(EjlDeviceParmConfig ejlDeviceParmConfig);
}
