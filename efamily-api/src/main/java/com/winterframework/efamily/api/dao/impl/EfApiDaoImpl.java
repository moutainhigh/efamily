package com.winterframework.efamily.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IEfApiDao;
import com.winterframework.efamily.dao.impl.EfComApiDaoImpl;
import com.winterframework.efamily.entity.EfApi;

@Repository("efApiDaoImpl")
public class EfApiDaoImpl extends EfComApiDaoImpl<EfApi> implements IEfApiDao {
 
  
}