package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfPlatformHealthSetting;

public interface IEfPlatformHealthSettingService extends IBaseService<EfPlatformHealthSetting> { 
	EfPlatformHealthSetting getByAgeLevel(Integer ageLevel) throws BizException;
}
