package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfDeviceAlarmTargetDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfDeviceAlarmTarget;
 
@Repository("efDeviceAlarmTargetDaoImpl")
public class EfDeviceAlarmTargetDaoImpl<E extends EfDeviceAlarm> extends BaseDaoImpl<EfDeviceAlarmTarget> implements IEfDeviceAlarmTargetDao{
	@Override
	public List<EfDeviceAlarmTarget> getByStatus(Integer status) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		return this.sqlSessionTemplate.selectList(getQueryPath("getByStatus"), map);
	}
}
