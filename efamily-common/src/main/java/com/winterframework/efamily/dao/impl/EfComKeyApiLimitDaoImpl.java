package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComKeyApiLimitDao;
import com.winterframework.efamily.entity.EfKeyApiLimit;

@Repository("efComKeyApiLimitDaoImpl")
public class EfComKeyApiLimitDaoImpl<E extends EfKeyApiLimit> extends BaseDaoImpl<EfKeyApiLimit> implements IEfComKeyApiLimitDao {
	
}