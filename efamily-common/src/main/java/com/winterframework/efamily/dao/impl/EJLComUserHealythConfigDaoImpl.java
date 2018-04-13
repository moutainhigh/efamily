package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEJLComUserHealythConfigDao;
import com.winterframework.efamily.entity.UserHealtyConfig;

@Repository("eJLComUserHealythConfigDaoImpl")
public class EJLComUserHealythConfigDaoImpl <E extends UserHealtyConfig> extends BaseDaoImpl<UserHealtyConfig> implements IEJLComUserHealythConfigDao{

	@Override
	public UserHealtyConfig getByDeviceIdAndUser(Long userId, Long deviceId)
			throws BizException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByDeviceIdAndUser"), map);
	}
}
