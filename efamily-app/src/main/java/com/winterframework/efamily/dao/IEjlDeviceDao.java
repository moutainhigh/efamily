package com.winterframework.efamily.dao;

import java.util.Map;

import com.winterframework.efamily.entity.EjlDevice;

public interface IEjlDeviceDao extends IEjlComDeviceDao{ 

	public EjlDevice getByFamilyAndDevice(EjlDevice ejlDevice);
	
	public EjlDevice getEjlDeviceByParm(EjlDevice ejlDevice) ;
	
	public Map<String,String> getBaseDeviceParamMapBy(Long id);
}
