package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComKeyApiLimitDao;
import com.winterframework.efamily.entity.EfKeyApiLimit;
import com.winterframework.efamily.service.IEfComKeyApiLimitService;

@Service("efComKeyApiLimitServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComKeyApiLimitServiceImpl extends BaseServiceImpl<IEfComKeyApiLimitDao, EfKeyApiLimit>implements IEfComKeyApiLimitService{

	@Resource(name="efComKeyApiLimitDaoImpl")
	private IEfComKeyApiLimitDao dao;
	@Override
	protected IEfComKeyApiLimitDao getEntityDao() {
		return dao;
	}

}