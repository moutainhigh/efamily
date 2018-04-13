package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfUserHealthSetting;

public interface IEfUserHealthSettingService extends IBaseService<EfUserHealthSetting> { 
	EfUserHealthSetting getByUserId(Long userId) throws BizException;
}
