package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.ITestDao;
import com.winterframework.efamily.entity.Test;
 
@Repository("testDaoImpl")
public class TestDaoImpl<E extends Test> extends BaseDaoImpl<Test> implements ITestDao{ 
	public void test(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId); 
		sqlSessionTemplate.selectList(getQueryPath("getTest"), map);
	}
}
