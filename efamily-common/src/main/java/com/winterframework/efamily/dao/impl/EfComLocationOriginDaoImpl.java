package com.winterframework.efamily.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComLocationOriginDao;
import com.winterframework.efamily.entity.EfLocationOrigin;

@Repository("efComLocationOriginDaoImpl")
public class EfComLocationOriginDaoImpl<E extends EfLocationOrigin> extends BaseDaoImpl<EfLocationOrigin> implements IEfComLocationOriginDao {

	@Override
	public Integer updateStatus(Long id, Integer status) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", status);
		map.put("id", id);
		return this.sqlSessionTemplate.update(this.getQueryPath("updateStatus"), map);
	}

	@Override
	public List<EfLocationOrigin> getLocationOriginByDevice(Long userId,Long deviceId) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		return this.sqlSessionTemplate.selectList("getLocationOriginByDevice", map);
	}

	@Override
	public List<Map<String,Long>> getLocationOriginDeviceId() throws Exception {
		return this.sqlSessionTemplate.selectList(getQueryPath("getLocationOriginDeviceId"));
	}

	@Override
	public List<EfLocationOrigin> getLocationOriginByTime(Date beginTime,
			Date endTime, Long deviceId) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getLocationOriginByTime"), map);
	}

	@Override
	public List<EfLocationOrigin> getLocationOriginByDevice(Long userId,
			Long deviceId, Date beginTime, Date endTime) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList("getLocationOriginByDeviceAndTime", map);
	}

	@Override
	public List<Map<String, Long>> getLocationOriginDeviceId(Date beginTime,
			Date endTime) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList(getQueryPath("getLocationOriginDeviceId"));
	}
}
