package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComDeviceHealthyDataPushDao;
import com.winterframework.efamily.entity.EfDeviceHealthyDataPush;

@Repository("efComDeviceHealthyDataPushDaoImpl")
public class EfComDeviceHealthyDataPushDaoImpl<E extends EfDeviceHealthyDataPush> extends BaseDaoImpl<EfDeviceHealthyDataPush> implements IEfComDeviceHealthyDataPushDao{
	
	public List<EfDeviceHealthyDataPush> getDeviceHealthyDataPushList(Date fromTime, Date toTime, int status) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", status);
		map.put("fromTime", fromTime);
		map.put("toTime", toTime);
		return this.sqlSessionTemplate.selectList("getDeviceHealthyDataPushList", map);
	}
	
}
