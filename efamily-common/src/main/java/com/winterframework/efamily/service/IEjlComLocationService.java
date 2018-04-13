package com.winterframework.efamily.service;


import java.util.Date;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlLocation;


public interface IEjlComLocationService extends IBaseService<EjlLocation> {
	EjlLocation getUserLatestLocation(Long userId, Long watchId,Integer status,Date time);
	
	public EjlLocation getDeviceLocationLatest(String imei);
	
	EjlLocation getBySourceId(Long sourceId) throws BizException;
}
