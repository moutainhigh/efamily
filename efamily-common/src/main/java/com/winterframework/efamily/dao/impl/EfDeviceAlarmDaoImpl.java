package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfDeviceAlarmDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;
 
@Repository("efDeviceAlarmDaoImpl")
public class EfDeviceAlarmDaoImpl<E extends EfDeviceAlarm> extends BaseDaoImpl<EfDeviceAlarm> implements IEfDeviceAlarmDao{
	@Override
	public List<EfDeviceAlarm> getByUserIdAndDeviceId(Long userId,Long deviceId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId); 
		return this.sqlSessionTemplate.selectList(getQueryPath("getByUserIdAndDeviceId"), map);
	}
	@Override
	public List<EfDeviceAlarm> getByUserIdAndDeviceIdAndType(Long userId,
			Long deviceId, Integer type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("type", type);
		return this.sqlSessionTemplate.selectList(getQueryPath("getByUserIdAndDeviceIdAndType"), map);
	}
	
	@Override
	public List<EfDeviceAlarm> getByUserIdAndDeviceIdAndTypeAndTime(Long userId,
			Long deviceId, Integer type,Long time) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("type", type);
		map.put("time", time);
		return this.sqlSessionTemplate.selectList(getQueryPath("getByUserIdAndDeviceIdAndTypeAndTime"), map);
	}
}
