package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.DeviceLocationSatellite;

public interface IDeviceLocationSatelliteDao  extends IBaseDao<DeviceLocationSatellite>{ 
	List<DeviceLocationSatellite> getByDeviceId(Long deviceId) throws BizException;
	int deleteByDeviceId(Long deviceId) throws BizException;
}
