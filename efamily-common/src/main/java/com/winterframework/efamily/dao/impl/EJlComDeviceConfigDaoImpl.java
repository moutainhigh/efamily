package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComDeviceConfigDao;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;

@Repository("eJlComDeviceConfigDaoImpl")
public class EJlComDeviceConfigDaoImpl<E extends EjlDeviceParmConfig> extends BaseDaoImpl<EjlDeviceParmConfig> implements IEjlComDeviceConfigDao{

	//###############################    手动添加公用的SQL   #########################################
	@Override
	public EjlDeviceParmConfig getByDeviceIdAndKey(Long deviceId, String key) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("paramKey", key);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByDeviceIdAndKey"), map);
	}

	@Override
	public int updateByDeviceAndKey(EjlDeviceParmConfig ejlDeviceParmConfig) {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateByDeviceAndKey"), ejlDeviceParmConfig);
	} 

	
}
