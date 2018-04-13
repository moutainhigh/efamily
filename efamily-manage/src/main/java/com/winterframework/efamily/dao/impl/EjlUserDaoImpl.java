package com.winterframework.efamily.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dto.CustomerDeviceStatisticsDto;
import com.winterframework.efamily.dto.manage.UserMonitor;
import com.winterframework.efamily.dto.manage.UserStruc;
import com.winterframework.efamily.entity.EjlUser;

@Repository("ejlUserDaoImpl")
public class EjlUserDaoImpl extends EjlComUserDaoImpl<EjlUser> implements IEjlUserDao {

	@Override
	public List<UserMonitor> getStatistics() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return sqlSessionTemplate.selectList(getQueryPath("getStatistics"), map);
	}
	@Override
	public List<UserStruc> getUserList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return sqlSessionTemplate.selectList(getQueryPath("getUserList"), map);
	}
	public List<CustomerDeviceStatisticsDto> getNumberModelStatistcsList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return sqlSessionTemplate.selectList(getQueryPath("getNumberModelStatistcsList"), map);
	}
	
	public List<CustomerDeviceStatisticsDto> getNumberModelStatusStatistcsList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return sqlSessionTemplate.selectList(getQueryPath("getNumberModelStatusStatistcsList"), map);
	}
	
}
