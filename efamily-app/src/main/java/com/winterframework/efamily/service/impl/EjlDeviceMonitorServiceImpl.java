package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlDeviceMonitorDao;
import com.winterframework.efamily.entity.EjlDeviceMonitor;
import com.winterframework.efamily.service.IEjlDeviceMonitorService;

@Service("ejlDeviceMonitorServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlDeviceMonitorServiceImpl extends BaseServiceImpl<IEjlDeviceMonitorDao, EjlDeviceMonitor>
		implements IEjlDeviceMonitorService {

	@Resource(name = "ejlDeviceMonitorDaoImpl")
	private IEjlDeviceMonitorDao ejlDeviceMonitorDaoImpl;

	@Override
	protected IEjlDeviceMonitorDao getEntityDao() {
		return ejlDeviceMonitorDaoImpl;
	}


}
