package com.winterframework.efamily.dao;


import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlUserDevice;

public interface IEjlComUserDeviceDao  extends IBaseDao<EjlUserDevice> {

	public EjlUserDevice getOnlyDeviceByUser(Long userId) throws Exception;
}
