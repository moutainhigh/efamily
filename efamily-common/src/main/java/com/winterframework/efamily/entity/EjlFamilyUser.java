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


public class EjlFamilyUser extends FmlBaseEntity {
	
	private static final long serialVersionUID = -2416670946390783851L;
	//alias
	public static final String TABLE_ALIAS = "EjlFamilyUser";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_FAMILY_ID = "familyId";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_TYPE = "type";
	public static final String ALIAS_ROLE_ID = "roleId";
	
	//date formats
	
	//columns START
	private Long userId;
	private Long familyId;
	private Long status;
	private Long type;
	private Long roleId;
	private Long manageType;
	private Integer position;
	private Integer isForbitSpeak;
	//columns END
	public enum Status{ 
		PRESENT(0),
		ABSENT(1);
		private int _value;
		Status(int value){ 
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}
	public enum Type{ 
		APP(1),
		WATCH(2),
		NONE(3);
		private int _value;
		Type(int value){ 
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}

	public enum Position{ 
		MEMBER(1),
		HOST(2),
		MANAGER(3);
		private int _value;
		Position(int value){ 
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}
	public EjlFamilyUser(){
	}

	public EjlFamilyUser(
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
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
	
    public Long getManageType() {
		return manageType;
	}

	public void setManageType(Long manageType) {
		this.manageType = manageType;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getIsForbitSpeak() {
		return isForbitSpeak;
	}

	public void setIsForbitSpeak(Integer isForbitSpeak) {
		this.isForbitSpeak = isForbitSpeak;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("FamilyId",getFamilyId())		
		.append("Status",getStatus())		
		.append("Type",getType())		
		.append("GmtCreated",getGmtCreated())		
		.append("RoleId",getRoleId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getFamilyId())
		.append(getStatus())
		.append(getType())
		.append(getGmtCreated())
		.append(getRoleId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlFamilyUser == false) return false;
		if(this == obj) return true;
		EjlFamilyUser other = (EjlFamilyUser)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getFamilyId(),other.getFamilyId())

		.append(getStatus(),other.getStatus())

		.append(getType(),other.getType())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getRoleId(),other.getRoleId())

			.isEquals();
	}
}

