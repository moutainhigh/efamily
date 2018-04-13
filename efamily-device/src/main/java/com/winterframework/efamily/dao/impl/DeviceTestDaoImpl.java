package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IDeviceTestDao;
import com.winterframework.efamily.entity.Test;

@Repository("deviceTestDaoImpl")
public class DeviceTestDaoImpl extends TestDaoImpl<Test> implements IDeviceTestDao{ 
	public void test(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId); 
		sqlSessionTemplate.selectList(getQueryPath("getTest"), map);
	}
}
