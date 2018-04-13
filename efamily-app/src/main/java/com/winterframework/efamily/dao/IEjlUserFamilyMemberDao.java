 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.dao;


import com.winterframework.efamily.entity.EjlUserFamilyMember;

public interface IEjlUserFamilyMemberDao  extends IEjlComUserFamilyMemberDao {

	public EjlUserFamilyMember getByAttribute(EjlUserFamilyMember ejlUserFamilyMember) ;
	public int updateByUserAndMemberId(EjlUserFamilyMember ejlUserFamilyMember) ;
	public int updateByUserAndFamilyId(EjlUserFamilyMember ejlUserFamilyMember) ;
}
