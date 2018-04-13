package com.winterframework.efamily.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComRoleDao;
import com.winterframework.efamily.entity.EjlRole;
import com.winterframework.efamily.service.IEjlComRoleService;

@Service("ejlComRoleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComRoleServiceImpl extends BaseServiceImpl<IEjlComRoleDao,EjlRole> implements IEjlComRoleService {
 
	@Resource(name="ejlComRoleDaoImpl")
	private IEjlComRoleDao ejlComRoleDaoImpl;
	@Override
	protected IEjlComRoleDao getEntityDao() { 
		return ejlComRoleDaoImpl;
	}
			
}


