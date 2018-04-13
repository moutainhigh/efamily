package com.winterframework.efamily.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfDeviceHealthyDataPush;

public interface IEfComDeviceHealthyDataPushDao extends IBaseDao<EfDeviceHealthyDataPush>{

	public List<EfDeviceHealthyDataPush> getDeviceHealthyDataPushList(Date fromTime, Date toTime, int status) throws Exception ;
	
}
