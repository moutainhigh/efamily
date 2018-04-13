package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComKeyIpDao;
import com.winterframework.efamily.entity.EfKeyIp;
import com.winterframework.efamily.service.IEfComKeyIpService;

@Service("efComKeyIpServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComKeyIpServiceImpl extends BaseServiceImpl<IEfComKeyIpDao, EfKeyIp>implements IEfComKeyIpService{

	@Resource(name="efComKeyIpDaoImpl")
	private IEfComKeyIpDao dao;
	@Override 
	protected IEfComKeyIpDao getEntityDao() {
		return dao;
	}

}