package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IDeviceLocationSatelliteDao;
import com.winterframework.efamily.entity.DeviceLocationSatellite;
 
@Repository("deviceLocationSatelliteDaoImpl")
public class DeviceLocationSatelliteDaoImpl<E extends DeviceLocationSatellite> extends BaseDaoImpl<DeviceLocationSatellite> implements IDeviceLocationSatelliteDao{
	@Override
	public List<DeviceLocationSatellite> getByDeviceId(Long deviceId)
			throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", deviceId); 
		return sqlSessionTemplate.selectList(getQueryPath("getByDeviceId"), map);
	}
	@Override
	public int deleteByDeviceId(Long deviceId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", deviceId); 
		return sqlSessionTemplate.delete(getQueryPath("deleteByDeviceId"), map);
	}
	
}
