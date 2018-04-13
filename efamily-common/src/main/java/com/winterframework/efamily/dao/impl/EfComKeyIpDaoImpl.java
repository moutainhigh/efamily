package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComKeyIpDao;
import com.winterframework.efamily.entity.EfKeyIp;

@Repository("efComKeyIpDaoImpl")
public class EfComKeyIpDaoImpl<E extends EfKeyIp> extends BaseDaoImpl<EfKeyIp> implements IEfComKeyIpDao {
	
}