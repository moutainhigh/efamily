package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IDeviceLocationSatelliteDao;
import com.winterframework.efamily.entity.DeviceLocationSatellite;
import com.winterframework.efamily.service.IDeviceLocationSatelliteService;

@Service("deviceLocationSatelliteServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceLocationSatelliteServiceImpl  extends BaseServiceImpl<IDeviceLocationSatelliteDao,DeviceLocationSatellite> implements IDeviceLocationSatelliteService {
	@Resource(name="deviceLocationSatelliteDaoImpl")
	private IDeviceLocationSatelliteDao dao;
	@Override
	protected IDeviceLocationSatelliteDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	@Override
	public List<DeviceLocationSatellite> queryByDeviceId(Long deviceId) throws BizException {
		return dao.getByDeviceId(deviceId);
	}
	@Override
	public int removeByDeviceId(Long deviceId) throws BizException {
		return dao.deleteByDeviceId(deviceId);
	}
}
