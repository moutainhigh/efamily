package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlUserFamilyMemberDao;
import com.winterframework.efamily.entity.EjlUserFamilyMember;


@Repository("ejlUserFamilyMemberDaoImpl")
public class EjlUserFamilyMemberDaoImpl extends EjlComUserFamilyMemberDaoImpl<EjlUserFamilyMember> implements IEjlUserFamilyMemberDao  {

	public EjlUserFamilyMember getByAttribute(EjlUserFamilyMember ejlUserFamilyMember) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByAttribute"), ejlUserFamilyMember);
	}
	
	public int updateByUserAndMemberId(EjlUserFamilyMember ejlUserFamilyMember) {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateByUserAndMemberId"), ejlUserFamilyMember);
	}
	
	public int updateByUserAndFamilyId(EjlUserFamilyMember ejlUserFamilyMember) {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateByUserAndFamilyId"), ejlUserFamilyMember);
	}
	
}
