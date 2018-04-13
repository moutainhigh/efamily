package com.winterframework.efamily.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IEfApiLimitDao;
import com.winterframework.efamily.dao.impl.EfComApiLimitDaoImpl;
import com.winterframework.efamily.entity.EfApiLimit;

@Repository("efApiLimitDaoImpl")
public class EfApiLimitDaoImpl extends EfComApiLimitDaoImpl<EfApiLimit> implements IEfApiLimitDao {
 
  
}