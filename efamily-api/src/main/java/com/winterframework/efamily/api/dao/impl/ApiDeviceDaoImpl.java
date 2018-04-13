package com.winterframework.efamily.api.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IApiDeviceDao;
import com.winterframework.efamily.dao.impl.EjlComDeviceDaoImpl;
import com.winterframework.efamily.entity.EjlDevice;

@Repository("apiDeviceDaoImpl")
public class ApiDeviceDaoImpl extends EjlComDeviceDaoImpl<EjlDevice> implements IApiDeviceDao {

	@Override
	public List<EjlDevice> getListByCustomerIdAndStatusAndTime(Long customerId,
			Integer status, Date time) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("customerId", customerId);
		map.put("status", status);
		map.put("time", time);
		return this.sqlSessionTemplate.selectList("getListByCustomerIdAndStatusAndTime", map);
	}
}
