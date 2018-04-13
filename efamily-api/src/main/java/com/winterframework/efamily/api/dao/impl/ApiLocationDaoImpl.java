package com.winterframework.efamily.api.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IApiLocationDao;
import com.winterframework.efamily.dao.impl.EjlComLocationDaoImpl;
import com.winterframework.efamily.entity.EjlLocation;

@Repository("apiLocationDaoImpl")
public class ApiLocationDaoImpl extends EjlComLocationDaoImpl<EjlLocation> implements IApiLocationDao {
	public List<EjlLocation> getListAfterByDeviceIdAndTime(Long deviceId, Date bizTime,Long userId) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("time", bizTime);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectList("getListAfterByDeviceIdAndTime", map);
	}

	@Override
	public List<EjlLocation> getListBetweenByDeviceIdAndTime(Long deviceId,
			Date startTime, Date endTime,Long userId) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("time", startTime);
		map.put("endTime", endTime);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectList("getListBetweenByDeviceIdAndTime", map);
	}
	
	
	
}
