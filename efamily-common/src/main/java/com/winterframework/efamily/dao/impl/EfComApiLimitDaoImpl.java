package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComApiLimitDao;
import com.winterframework.efamily.entity.EfApiLimit;

@Repository("efComApiLimitDaoImpl")
public class EfComApiLimitDaoImpl<E extends EfApiLimit> extends BaseDaoImpl<EfApiLimit> implements IEfComApiLimitDao {
	
}