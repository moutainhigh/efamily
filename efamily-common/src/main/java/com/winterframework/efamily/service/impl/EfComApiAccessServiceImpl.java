package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComApiAccessDao;
import com.winterframework.efamily.entity.EfApiAccess;
import com.winterframework.efamily.service.IEfComApiAccessService;

@Service("efComApiAccessServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComApiAccessServiceImpl extends BaseServiceImpl<IEfComApiAccessDao, EfApiAccess>implements IEfComApiAccessService{

	@Resource(name="efComApiAccessDaoImpl")
	private IEfComApiAccessDao dao;
	@Override
	protected IEfComApiAccessDao getEntityDao() {
		return dao;
	}

}
