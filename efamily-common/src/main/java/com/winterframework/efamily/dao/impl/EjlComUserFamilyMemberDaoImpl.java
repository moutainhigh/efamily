package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserFamilyMemberDao;
import com.winterframework.efamily.entity.EjlUserFamilyMember;


@Repository("ejlComUserFamilyMemberDaoImpl")
public class EjlComUserFamilyMemberDaoImpl<E extends EjlUserFamilyMember> extends BaseDaoImpl<EjlUserFamilyMember> implements IEjlComUserFamilyMemberDao  {

	
}
