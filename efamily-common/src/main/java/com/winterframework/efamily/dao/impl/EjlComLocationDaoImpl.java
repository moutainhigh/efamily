package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.entity.EjlLocation;

@Repository("ejlComLocationDaoImpl")
public class EjlComLocationDaoImpl<E extends EjlLocation> extends BaseDaoImpl<EjlLocation> implements IEjlComLocationDao {

	@Override
	public EjlLocation getUserLatestLocation(Long userId, Long watchId,Integer status,Date time) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("watchId", watchId);
		map.put("status", status);
		map.put("time", time);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getUserLatestLocation"), map);
	}
	
	@Override
	public EjlLocation getDeviceLocationLatest(String imei) {
		if(imei==null){
			return null;
		}
		return this.sqlSessionTemplate.selectOne(getQueryPath("getDeviceLocationLatest"), imei);
	}
	
	@Override
	public List<EjlLocation> getUserLocation(Long userId, Long watchId,Date time) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("watchId", watchId);
		map.put("time", time);
		return this.sqlSessionTemplate.selectList(getQueryPath("getUserLocation"), map);
	}

	@Override
	public List<EjlLocation> getUserLocationDesc(Long userId, Long watchId,Date time) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("watchId", watchId);
		map.put("time", time);
		return this.sqlSessionTemplate.selectList(getQueryPath("getUserLocationDesc"), map);
	}
	
	
	@Override
	public List<EjlLocation> getUserLocationLatelyCount(Long userId, Long watchId,Date time,Long locationCount) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("watchId", watchId);
		map.put("time", time);
		map.put("locationCount", locationCount);
		
		return this.sqlSessionTemplate.selectList("getUserLocationLatelyCount", map);
	}
	
	public Integer updateStatus(Long locationId,Integer status ) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", status);
		map.put("id", locationId);
		return this.sqlSessionTemplate.update("updateStatus", map);
	}
	@Override
	public EjlLocation getBySourceId(Long sourceId) throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("sourceId", sourceId);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getBySourceId"), map);
	}
}
