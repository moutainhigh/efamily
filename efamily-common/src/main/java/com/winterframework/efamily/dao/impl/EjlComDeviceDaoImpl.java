package com.winterframework.efamily.dao.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.entity.EjlDevice;

@Repository("ejlComDeviceDaoImpl")
public class EjlComDeviceDaoImpl<E extends EjlDevice> extends BaseDaoImpl<EjlDevice>  implements IEjlComDeviceDao  {

	@Override
	public EjlDevice getByImei(String imei) throws Exception{
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByImei"), imei);
	}
	@Override
	public List<EjlDevice> getByType(Integer type) throws Exception {
		return this.sqlSessionTemplate.selectList(getQueryPath("getByType"), type);
	}
	@Override
	public List<EjlDevice> getDeviceByTypeAndStatus(Integer type, Integer status)
			throws Exception {
		Map map = new HashMap();
		map.put("type", type);
		map.put("status", status);
		return this.sqlSessionTemplate.selectList(getQueryPath("getDeviceByTypeAndStatus"), map);
	}
	//###############################    手动添加公用的SQL   #########################################
	@Override
	public EjlDevice getEjlDeviceByParm(EjlDevice ejlDevice) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEjlDeviceByParm"), ejlDevice);
	}
	
	//###############################    手动添加业务使用的SQL   #####################################

	@Override
	public EjlDevice getByFamilyAndDevice(EjlDevice ejlDevice) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByFamilyAndDevice"), ejlDevice);
	}
	
}
