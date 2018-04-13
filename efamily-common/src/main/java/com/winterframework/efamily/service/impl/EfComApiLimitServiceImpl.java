package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComApiLimitDao;
import com.winterframework.efamily.entity.EfApiLimit;
import com.winterframework.efamily.service.IEfComApiLimitService;

@Service("efComApiLimitServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComApiLimitServiceImpl extends BaseServiceImpl<IEfComApiLimitDao, EfApiLimit>implements IEfComApiLimitService{

	@Resource(name="efComApiLimitDaoImpl")
	private IEfComApiLimitDao dao;
	@Override
	protected IEfComApiLimitDao getEntityDao() {
		return dao;
	}

}