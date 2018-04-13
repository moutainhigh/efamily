package com.winterframework.efamily.dao;
 
import java.util.List;

import com.winterframework.efamily.entity.EjlDeviceParmConfig;

public interface IEJLDeviceConfigDao extends IEjlComDeviceConfigDao{ 
	
	/**
	 * 功能：根据设备号和KEY 获取设备参数信息
	 * @param ejlDeviceParmConfig
	 * @return
	 */
	public EjlDeviceParmConfig getByDeviceAndKey(EjlDeviceParmConfig ejlDeviceParmConfig);
	
	/**
	 * 功能：根据设备号和KEY 修改设备参数信息
	 * @param ejlDeviceParmConfig
	 * @return
	 */
	public int updateByDeviceAndKey(EjlDeviceParmConfig ejlDeviceParmConfig);
	/**
	 * 
	* @Title: getObjByAttribute 
	* @Description: TODO() 
	* @param ejlDeviceParmConfig
	* @return
	 */
	public List<EjlDeviceParmConfig> getObjByAttribute(EjlDeviceParmConfig ejlDeviceParmConfig);
}
