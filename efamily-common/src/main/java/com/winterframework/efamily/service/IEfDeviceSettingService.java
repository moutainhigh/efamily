package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfDeviceSetting;

public interface IEfDeviceSettingService extends IBaseService<EfDeviceSetting> { 
	EfDeviceSetting getByUserIdAndDeviceId(Long userId,Long deviceId) throws BizException;
}
