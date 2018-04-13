package com.winterframework.efamily.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IEfKeyIpDao;
import com.winterframework.efamily.dao.impl.EfComKeyIpDaoImpl;
import com.winterframework.efamily.entity.EfKeyIp;

@Repository("efKeyIpDaoImpl")
public class EfKeyIpDaoImpl extends EfComKeyIpDaoImpl<EfKeyIp> implements IEfKeyIpDao {
 
  
}