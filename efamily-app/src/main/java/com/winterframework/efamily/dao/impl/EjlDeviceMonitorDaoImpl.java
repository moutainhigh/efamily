package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlDeviceMonitorDao;
import com.winterframework.efamily.entity.EjlDeviceMonitor;


@Repository("ejlDeviceMonitorDaoImpl")
public class EjlDeviceMonitorDaoImpl extends EjlComDeviceMonitorDaoImpl<EjlDeviceMonitor>  implements IEjlDeviceMonitorDao  {	
	
}
