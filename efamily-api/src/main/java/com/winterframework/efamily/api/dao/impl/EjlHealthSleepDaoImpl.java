package com.winterframework.efamily.api.dao.impl;


import java.util.List;


import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IEjlHealthSleepDao;

import com.winterframework.efamily.dao.impl.EjlComHealthSleepDaoImpl;

import com.winterframework.efamily.entity.EjlHealthSleep;


@Repository("ejlHealthSleepDaoImpl")
public class EjlHealthSleepDaoImpl extends EjlComHealthSleepDaoImpl<EjlHealthSleep> implements IEjlHealthSleepDao{

	@Override
	public List<EjlHealthSleep> getSleepsByTime(EjlHealthSleep ejlHealthSleep)
			throws Exception {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getSleepsByTime"), ejlHealthSleep);
	}
}
