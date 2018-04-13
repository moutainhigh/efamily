package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComFunctionDao;
import com.winterframework.efamily.entity.EjlFunction;
import com.winterframework.efamily.service.IEjlComFunctionService;

@Service("ejlComFunctionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComFunctionServiceImpl extends BaseServiceImpl<IEjlComFunctionDao,EjlFunction> implements IEjlComFunctionService {
	@Resource(name="ejlComFunctionDaoImpl")
	private IEjlComFunctionDao ejlComFunctionDaoImpl;
	@Override
	protected IEjlComFunctionDao getEntityDao() { 
		return ejlComFunctionDaoImpl;
	}
}


