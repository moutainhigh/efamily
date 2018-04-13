package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfDeviceAlarmTarget;

public interface IEfDeviceAlarmTargetDao  extends IBaseDao<EfDeviceAlarmTarget>{ 
	
	List<EfDeviceAlarmTarget> getByStatus(Integer status) throws Exception;
}
