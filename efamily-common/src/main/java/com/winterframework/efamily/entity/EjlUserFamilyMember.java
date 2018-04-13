 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlUserFamilyMember extends FmlBaseEntity {
	
	private static final long serialVersionUID = 2254859044377168588L;
	//alias
	public static final String TABLE_ALIAS = "EjlUserFamilyMember";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_FAMILY_ID = "家庭ID";
	public static final String ALIAS_MEMBER_ID = "成员ID EJL_USER.ID";
	public static final String ALIAS_REMARK_NAME = "备注";
	public static final String ALIAS_STATUS = "该用户是否为家庭成员 0 是 1 否";
	
	//date formats
			public static final String FORMAT_GMT_MODIFY = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private Long familyId;
	private Long memberId;
	private String remarkName;
	private Long status;
	//columns END

	public EjlUserFamilyMember(){
	}

	public EjlUserFamilyMember(
		Long id
	){
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setFamilyId(Long value) {
		this.familyId = value;
	}
	
	public Long getFamilyId() {
		return this.familyId;
	}
	public void setMemberId(Long value) {
		this.memberId = value;
	}
	
	public Long getMemberId() {
		return this.memberId;
	}
	public void setRemarkName(String value) {
		this.remarkName = value;
	}
	
	public String getRemarkName() {
		return this.remarkName;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("FamilyId",getFamilyId())		
		.append("MemberId",getMemberId())		
		.append("RemarkName",getRemarkName())		
		.append("Status",getStatus())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getFamilyId())
		.append(getMemberId())
		.append(getRemarkName())
		.append(getStatus())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlUserFamilyMember == false) return false;
		if(this == obj) return true;
		EjlUserFamilyMember other = (EjlUserFamilyMember)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getFamilyId(),other.getFamilyId())

		.append(getMemberId(),other.getMemberId())

		.append(getRemarkName(),other.getRemarkName())

		.append(getStatus(),other.getStatus())


			.isEquals();
	}
}

