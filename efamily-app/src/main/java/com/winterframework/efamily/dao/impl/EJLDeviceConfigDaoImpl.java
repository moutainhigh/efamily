package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEJLDeviceConfigDao;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;

@Repository("deviceConfigDaoImpl")
public class EJLDeviceConfigDaoImpl extends EJlComDeviceConfigDaoImpl<EjlDeviceParmConfig> implements IEJLDeviceConfigDao{

	@Override
	public EjlDeviceParmConfig getByDeviceAndKey(EjlDeviceParmConfig ejlDeviceParmConfig) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByDeviceAndKey"), ejlDeviceParmConfig);
	}

	@Override
	public List<EjlDeviceParmConfig> getObjByAttribute(EjlDeviceParmConfig ejlDeviceParmConfig) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getObjByAttribute"), ejlDeviceParmConfig);
	}
	
	
	@Override
	public int updateByDeviceAndKey(EjlDeviceParmConfig ejlDeviceParmConfig) {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateByDeviceAndKey"), ejlDeviceParmConfig);
	} 
}
