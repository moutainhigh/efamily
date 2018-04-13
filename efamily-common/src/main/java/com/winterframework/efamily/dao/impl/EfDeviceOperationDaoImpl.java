package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfDeviceOperationDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfDeviceOperation;
 
@Repository("efDeviceOperationDaoImpl")
public class EfDeviceOperationDaoImpl<E extends EfDeviceOperation> extends BaseDaoImpl<EfDeviceOperation> implements IEfDeviceOperationDao{
	@Override
	public EfDeviceOperation getByUserIdAndDeviceId(Long userId,Long deviceId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByUserIdAndDeviceId"), map);
	}
	@Override
	public EfDeviceOperation getLastOneByUserIdAndDeviceIdAndCodeAndTime(
			Long userId, Long deviceId, Integer code, Long time)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("code", code);
		map.put("time", time);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getLastOneByUserIdAndDeviceIdAndCodeAndTime"), map);
	}
	@Override
	public EfDeviceOperation getByUserIdAndDeviceIdAndCodeAndStatusAndTime(Long userId,
			Long deviceId, Integer code, Integer status, Long time) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("code", code);
		map.put("status", status);
		map.put("time", time);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByUserIdAndDeviceIdAndCodeAndStatusAndTime"), map);
	}
}
