package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfDeviceSettingDao;
import com.winterframework.efamily.entity.EfDeviceSetting;
 
@Repository("efDeviceSettingDaoImpl")
public class EfDeviceSettingDaoImpl<E extends EfDeviceSetting> extends BaseDaoImpl<EfDeviceSetting> implements IEfDeviceSettingDao{
	@Override
	public EfDeviceSetting getByUserIdAndDeviceId(Long userId,Long deviceId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByUserIdAndDeviceId"), map);
	}
}
