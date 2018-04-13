package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComRoleFunctionDao;
import com.winterframework.efamily.entity.EjlRoleFunction;
import com.winterframework.efamily.service.IEjlComRoleFunctionService;

@Service("ejlComRoleFunctionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComRoleFunctionServiceImpl extends BaseServiceImpl<IEjlComRoleFunctionDao,EjlRoleFunction> implements IEjlComRoleFunctionService {
	@Resource(name="ejlComRoleFunctionDaoImpl")
	private IEjlComRoleFunctionDao ejlComRoleFunctionDaoImpl;
	@Override
	protected IEjlComRoleFunctionDao getEntityDao() { 
		return ejlComRoleFunctionDaoImpl;
	}
}


