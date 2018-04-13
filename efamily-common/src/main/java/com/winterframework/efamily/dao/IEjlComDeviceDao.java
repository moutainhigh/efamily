package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlDevice;

public interface IEjlComDeviceDao extends IBaseDao<EjlDevice>{ 
	public EjlDevice getByImei(String imei) throws Exception;
	public List<EjlDevice> getByType(Integer type) throws Exception;
	public EjlDevice getByFamilyAndDevice(EjlDevice ejlDevice);
	
	public EjlDevice getEjlDeviceByParm(EjlDevice ejlDevice) ;
	
	public List<EjlDevice> getDeviceByTypeAndStatus(Integer type, Integer status) throws Exception;
}
