package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserFamilyMemberDao;
import com.winterframework.efamily.entity.EjlUserFamilyMember;
import com.winterframework.efamily.service.IEjlComUserFamilyMemberService;

@Service("ejlComUserFamilyMemberServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserFamilyMemberServiceImpl extends BaseServiceImpl<IEjlComUserFamilyMemberDao,EjlUserFamilyMember> implements IEjlComUserFamilyMemberService {
 
	@Resource(name="ejlComUserFamilyMemberDaoImpl")
	private IEjlComUserFamilyMemberDao ejlComUserFamilyMemberDaoImpl;
	@Override
	protected IEjlComUserFamilyMemberDao getEntityDao() { 
		return ejlComUserFamilyMemberDaoImpl;
	}
			
}


