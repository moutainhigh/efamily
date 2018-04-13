package com.winterframework.efamily.dao;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;

import com.winterframework.efamily.entity.UserHealtyConfig;

public interface IEJLComUserHealythConfigDao extends IBaseDao<UserHealtyConfig> {

	public UserHealtyConfig getByDeviceIdAndUser(Long userId,Long deviceId) throws BizException;
	
}
