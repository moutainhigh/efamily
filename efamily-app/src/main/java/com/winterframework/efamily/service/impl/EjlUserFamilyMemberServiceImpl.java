package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlUserFamilyMemberDao;
import com.winterframework.efamily.entity.EjlUserFamilyMember;
import com.winterframework.efamily.service.IEjlUserFamilyMemberService;

@Service("ejlUserFamilyMemberServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlUserFamilyMemberServiceImpl extends BaseServiceImpl<IEjlUserFamilyMemberDao,EjlUserFamilyMember> implements IEjlUserFamilyMemberService {
 
	@Resource(name="ejlUserFamilyMemberDaoImpl")
	private IEjlUserFamilyMemberDao ejlUserFamilyMemberDaoImpl;
	@Override
	protected IEjlUserFamilyMemberDao getEntityDao() { 
		return ejlUserFamilyMemberDaoImpl;
	}
			
}


