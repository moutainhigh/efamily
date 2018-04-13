package com.winterframework.efamily.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IEfKeyApiLimitDao;
import com.winterframework.efamily.dao.impl.EfComKeyApiLimitDaoImpl;
import com.winterframework.efamily.entity.EfKeyApiLimit;

@Repository("efKeyApiLimitDaoImpl")
public class EfKeyApiLimitDaoImpl extends EfComKeyApiLimitDaoImpl<EfKeyApiLimit> implements IEfKeyApiLimitDao {
 
  
}