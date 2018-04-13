package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.entity.EjlUserDevice;

@Repository("ejlComUserDeviceDaoImpl")
public class EjlComUserDeviceDaoImpl<E extends EjlUserDevice> extends BaseDaoImpl<EjlUserDevice> implements
		IEjlComUserDeviceDao {
	/**
	* Title: getOnlyDeviceByUser
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlComUserDeviceDao#getOnlyDeviceByUser(java.lang.Long) 
	*/
	@Override
	public EjlUserDevice getOnlyDeviceByUser(Long userId) throws Exception {
		EjlUserDevice ejlUserDevice = new EjlUserDevice();
		ejlUserDevice.setStatus(1l);
		ejlUserDevice.setUserId(userId);
		List<EjlUserDevice> list = this.sqlSessionTemplate.selectList(this.getQueryPath("getObjByAttribute"),
				ejlUserDevice);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
