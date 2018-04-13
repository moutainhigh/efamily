 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.dao.impl;

 import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEJLComHealthSleepDao;
import com.winterframework.efamily.entity.EjlHealthSleep;


@Repository("ejlComHealthSleepDaoImpl")
public class EjlComHealthSleepDaoImpl<E extends EjlHealthSleep> extends BaseDaoImpl<EjlHealthSleep> implements IEJLComHealthSleepDao {

	@Override
	public EjlHealthSleep getLastSleepByAttribute(EjlHealthSleep ejlHealthSleep)
			throws BizException {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getLastSleepByAttribute"), ejlHealthSleep);
	}

	@Override
	public List<EjlHealthSleep> getSleepsByAttribute(
			EjlHealthSleep ejlHealthSleep) throws BizException {
		return this.selectListObjByAttribute(ejlHealthSleep);
	}
}
