package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlFunctionDao;
import com.winterframework.efamily.entity.EjlFunction;
import com.winterframework.efamily.service.IEjlFunctionService;

@Service("ejlFunctionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlFunctionServiceImpl extends BaseServiceImpl<IEjlFunctionDao,EjlFunction> implements IEjlFunctionService {
	@Resource(name="ejlFunctionDaoImpl")
	private IEjlFunctionDao ejlFunctionDao;
	@Override
	protected IEjlFunctionDao getEntityDao() { 
		return ejlFunctionDao;
	}
}


