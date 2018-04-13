package com.winterframework.efamily.dao;
 

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;

public interface IEjlComDeviceConfigDao extends IBaseDao<EjlDeviceParmConfig>{ 
	
	/**
	 * 功能：根据设备号和KEY 获取设备参数信息
	 * @param ejlDeviceParmConfig
	 * @return
	 */
	EjlDeviceParmConfig getByDeviceIdAndKey(Long deviceId,String key) throws Exception;
	
	/**
	 * 功能：根据设备号和KEY 修改设备参数信息
	 * @param ejlDeviceParmConfig
	 * @return
	 */
	public int updateByDeviceAndKey(EjlDeviceParmConfig ejlDeviceParmConfig);
	
}
