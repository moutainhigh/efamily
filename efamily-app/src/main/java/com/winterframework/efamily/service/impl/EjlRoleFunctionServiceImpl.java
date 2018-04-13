package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlRoleFunctionDao;
import com.winterframework.efamily.entity.EjlRoleFunction;
import com.winterframework.efamily.service.IEjlRoleFunctionService;

@Service("ejlRoleFunctionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlRoleFunctionServiceImpl extends BaseServiceImpl<IEjlRoleFunctionDao,EjlRoleFunction> implements IEjlRoleFunctionService {
	@Resource(name="ejlRoleFunctionDaoImpl")
	private IEjlRoleFunctionDao ejlRoleFunctionDao;
	@Override
	protected IEjlRoleFunctionDao getEntityDao() { 
		return ejlRoleFunctionDao;
	}
}


