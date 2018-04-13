package com.winterframework.efamily.dao;


import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlUserDevice;

public interface IEjlUserDeviceDao  extends IBaseDao<EjlUserDevice> {

	/**
	 * 功能：获取用户设备
	 * @param ejlUserDevice
	 * @return
	 */
	public EjlUserDevice getEjlUserDevice(EjlUserDevice ejlUserDevice);

 
	/**
	 * 功能：删除用户设备
	 * @param ejlUserDevice
	 * @return
	 */
	public int deleteByDeviceSwitch(EjlUserDevice ejlUserDevice);
	
	public int updateByAttribute(EjlUserDevice ejlUserDevice);
	
	public EjlUserDevice getByAttribute(EjlUserDevice ejlUserDevice);
	
	public EjlUserDevice getEjlUserDeviceByDeviceCode(String deviceCode);
	
}
