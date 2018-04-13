package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComMessageMarkDao;
import com.winterframework.efamily.entity.EjlMessageMark;
import com.winterframework.efamily.service.IEjlComMessageMarkService;

@Service("ejlComMessageMarkServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComMessageMarkServiceImpl extends BaseServiceImpl<IEjlComMessageMarkDao, EjlMessageMark>
		implements IEjlComMessageMarkService {

	@Resource(name = "ejlComMessageMarkDaoImpl")
	private IEjlComMessageMarkDao ejlComMessageMarkDaoImpl;

	@Override
	protected IEjlComMessageMarkDao getEntityDao() {
		return ejlComMessageMarkDaoImpl;
	}


}