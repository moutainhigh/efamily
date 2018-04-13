package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;

public interface IDeviceParamConfigService extends IEjlComDeviceConfigService{
	void checkAndSetting(Context ctx,EjlDeviceParmConfig param) throws BizException;
}
