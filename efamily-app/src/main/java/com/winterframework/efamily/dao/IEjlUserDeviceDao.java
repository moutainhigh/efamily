package com.winterframework.efamily.dao;


import java.util.List;

import com.winterframework.efamily.entity.EjlUserDevice;

public interface IEjlUserDeviceDao  extends IEjlComUserDeviceDao {

	/**
	 * 功能：获取用户设备
	 * @param ejlUserDevice
	 * @return
	 */
	public EjlUserDevice getEjlUserDevice(EjlUserDevice ejlUserDevice);

	
	public EjlUserDevice getEjlUserDeviceByDeviceCode(String deviceCode) ;
	/**
	 * 功能：获取用户设备关系
	 * @param ejlUserDevice
	 * @return
	 */
	public EjlUserDevice getEjlUserDeviceByuserIdOrDeviceId(EjlUserDevice ejlUserDevice);
	/**
	 * 功能：删除用户设备
	 * @param ejlUserDevice
	 * @return
	 */
	public int deleteByDeviceSwitch(EjlUserDevice ejlUserDevice);
	/**
	 * 功能：修改用户设备关系
	 * @param ejlUserDevice
	 * @return
	 */
	public int updateByAttribute(EjlUserDevice ejlUserDevice);
	
	/**
	 * 功能：查询一个家庭中 正在使用的设备
	 * @param familyId
	 * @return
	 */
	public List<EjlUserDevice> getEjlUserDeviceByFamilyAndStatus(Long familyId);
}
