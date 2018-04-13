package com.winterframework.efamily.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IEjlHealthStepCountDao;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.impl.EjlComHealthStepCountDaoImpl;
import com.winterframework.efamily.entity.EjlHealthStepCount;

@Repository("ejlHealthStepCountDaoImpl")
public class EjlHealthStepCountDaoImpl extends EjlComHealthStepCountDaoImpl<EjlHealthStepCount> implements IEjlHealthStepCountDao{

	@Override
	public List<EjlHealthStepCount> getHealthStepCountByTime(Long  deviceId,
			Long fromTime, Long endTime,Long userId) throws Exception {
		Map map = new HashMap();
		map.put("deviceId", deviceId);
		map.put("startQueryTime", DateUtils.convertLong2Date(fromTime));
		map.put("endQueryTime", DateUtils.convertLong2Date(endTime));
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getHealthStepCountByTime"), map);
	}

	@Override
	public EjlHealthStepCount getLastHealthStepCountByTime(Long deviceId,Long userId) throws Exception {
		Map map = new HashMap();
		map.put("deviceId", deviceId);
		map.put("userId", userId);
		map.put("startQueryTime", DateUtils.addDays(DateUtils.currentDate(), -3));
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getHealthStepCountLastByTime"), map);
	}
	
	
}
