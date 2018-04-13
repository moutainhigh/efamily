package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfPlatformDeviceVersionDao;
import com.winterframework.efamily.entity.EfPlatformDeviceVersion;
 
@Repository("efPlatformDeviceVersionDaoImpl")
public class EfPlatformDeviceVersionDaoImpl<E extends EfPlatformDeviceVersion> extends BaseDaoImpl<EfPlatformDeviceVersion> implements IEfPlatformDeviceVersionDao{
	@Override
	public EfPlatformDeviceVersion getLatestByDeviceTypeAndDeviceModelAndCustomer(
			Integer deviceType, String deviceModel,Integer customerNumber) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceType", deviceType);
		map.put("deviceModel", deviceModel);
		map.put("customerNumber", customerNumber);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getLatestByDeviceTypeAndDeviceModelAndCustomer"), map);
	}
	@Override
	public EfPlatformDeviceVersion getByDeviceTypeAndDeviceModelAndDeviceVersion(
			Integer deviceType, String deviceModel, String deviceVersion)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceType", deviceType);
		map.put("deviceModel", deviceModel);
		map.put("deviceVersion", deviceVersion);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByDeviceTypeAndDeviceModelAndDeviceVersion"), map);
	}
}
