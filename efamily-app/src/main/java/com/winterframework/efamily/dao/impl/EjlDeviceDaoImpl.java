package com.winterframework.efamily.dao.impl;



import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlDeviceDao;
import com.winterframework.efamily.entity.EjlDevice;

@Repository("ejlDeviceDaoImpl")
public class EjlDeviceDaoImpl extends EjlComDeviceDaoImpl<EjlDevice>  implements IEjlDeviceDao  {

	@Override
	public EjlDevice getByFamilyAndDevice(EjlDevice ejlDevice) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByFamilyAndDevice"), ejlDevice);
	}
	@Override
	public EjlDevice getEjlDeviceByParm(EjlDevice ejlDevice) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEjlDeviceByParm"), ejlDevice);
	}
	@Override
	public Map<String, String> getBaseDeviceParamMapBy(Long id) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getBaseDeviceParamMapBy"), id);
	}
	
}
