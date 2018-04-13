package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEfLocationSemiDao;
import com.winterframework.efamily.entity.EfLocationSemi;

@Repository("efLocationSemiDaoImpl")
public class EfLocationSemiDaoImpl extends EfComLocationSemiDaoImpl<EfLocationSemi> implements IEfLocationSemiDao {
	@Override
	public List<EfLocationSemi> getListByTimeSpan(Long userId, Long deviceId,
			Date timeFrom, Date timeTo, Integer type) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		map.put("type", type);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListByTimeSpan"), map);
	}
}
