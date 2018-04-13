package com.winterframework.efamily.api.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.dao.IEfKeyApiLimitDao;
import com.winterframework.efamily.api.service.IEfKeyApiLimitService;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.entity.EfKeyApiLimit;

@Service("efKeyApiLimitServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfKeyApiLimitServiceImpl extends BaseServiceImpl<IEfKeyApiLimitDao,EfKeyApiLimit> implements IEfKeyApiLimitService {

	@Resource(name="efKeyApiLimitDaoImpl")
	private IEfKeyApiLimitDao efKeyApiLimitDaoImpl;
	
	@Override
	protected IEfKeyApiLimitDao getEntityDao() {
		
		return efKeyApiLimitDaoImpl;
	}


}

