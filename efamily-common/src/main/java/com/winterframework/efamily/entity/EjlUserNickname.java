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


public class EjlUserNickname extends FmlBaseEntity {
	
	private static final long serialVersionUID = 5783055299895238694L;
	//alias
	public static final String TABLE_ALIAS = "用户家庭昵称（用户昵称在每个人的app上显示都不一样）";
	public static final String ALIAS_FAMILY_ID = "家庭id";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_APP_USER_ID = "app用户id";
	public static final String ALIAS_NICK_NAME = "昵称（app用户设置user_id对应用户的昵称）";
	
	//date formats
	
	//columns START
	private Long familyId;
	private Long userId;
	private Long appUserId;
	private String nickName;
	//columns END

	public EjlUserNickname(){
	}

	public EjlUserNickname(
		Long id
	){
		this.id = id;
	}

	public void setFamilyId(Long value) {
		this.familyId = value;
	}
	
	public Long getFamilyId() {
		return this.familyId;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setAppUserId(Long value) {
		this.appUserId = value;
	}
	
	public Long getAppUserId() {
		return this.appUserId;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	
	public String getNickName() {
		return this.nickName;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("FamilyId",getFamilyId())		
		.append("UserId",getUserId())		
		.append("AppUserId",getAppUserId())		
		.append("NickName",getNickName())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getFamilyId())
		.append(getUserId())
		.append(getAppUserId())
		.append(getNickName())
		.append(getGmtCreated())
		.append(getGmtModified())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlUserNickname == false) return false;
		if(this == obj) return true;
		EjlUserNickname other = (EjlUserNickname)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getFamilyId(),other.getFamilyId())

		.append(getUserId(),other.getUserId())

		.append(getAppUserId(),other.getAppUserId())

		.append(getNickName(),other.getNickName())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

			.isEquals();
	}
}

