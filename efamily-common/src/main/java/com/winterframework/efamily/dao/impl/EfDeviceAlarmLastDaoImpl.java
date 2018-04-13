package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfDeviceAlarmLastDao;
import com.winterframework.efamily.entity.EfDeviceAlarmLast;
 
@Repository("efDeviceAlarmLastDaoImpl")
public class EfDeviceAlarmLastDaoImpl<E extends EfDeviceAlarmLast> extends BaseDaoImpl<EfDeviceAlarmLast> implements IEfDeviceAlarmLastDao{
	@Override
	public EfDeviceAlarmLast getByUserIdAndDeviceId(Long userId,Long deviceId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByUserIdAndDeviceId"), map);
	}

	@Override
	public EfDeviceAlarmLast getByUserIdAndType(Long userId,Integer type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("type", type); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByUserIdAndType"), map);
	}
	
	@Override
	public int closeUserExceptionRemind(Long userId) throws Exception {
		return this.sqlSessionTemplate.update(getQueryPath("closeUserExceptionRemind"), userId);
	}
	
	
}
