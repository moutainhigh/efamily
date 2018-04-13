package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfPlatformDeviceSettingDao;
import com.winterframework.efamily.entity.EfPlatformDeviceSetting;
 
@Repository("efPlatformDeviceSettingDaoImpl")
public class EfPlatformDeviceSettingDaoImpl<E extends EfPlatformDeviceSetting> extends BaseDaoImpl<EfPlatformDeviceSetting> implements IEfPlatformDeviceSettingDao{
	@Override
	public EfPlatformDeviceSetting getByDeviceType(Integer deviceType)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceType", deviceType); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByDeviceType"), map);
	}
}
