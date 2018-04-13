 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlUser extends FmlBaseEntity {
	
	private static final long serialVersionUID = -7471402061028745068L;
	//alias
	public static final String TABLE_ALIAS = "EjlUser";
	public static final String ALIAS_USER_NAME = "userName";
	public static final String ALIAS_NICK_NAME = "nickName";
	public static final String ALIAS_PHONE = "phone";
	public static final String ALIAS_PASSWD = "passwd";
	public static final String ALIAS_HEAD_IMG = "headImg";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_FAMILY_ID = "familyId";
	public static final String ALIAS_TYPE = "type";
	
	//date formats
	
	//columns START
	private String userName;
	private String nickName;
	private String phone;
	private String passwd;
	private String headImg;
	private Long status;
	private Long familyId;
	private Long type;
	private Long sex;
	private Long age;
	private Long height;
	private Double weight;
	private String signature;
	private String token;
	private Date lastLoginTime;
	private Integer appType; //用户归属
	//columns END

	public enum Type{
		APP(1),WATCH(2),NO_DEVICE(3);
		private int _code;
		Type(int code){
			_code=code;
		}
		public int getCode(){
			return _code;
		}
	}
	public EjlUser(){
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public EjlUser(
		Long id
	){
		this.id = id;
	}

	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	
	public String getNickName() {
		return this.nickName;
	}
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setPasswd(String value) {
		this.passwd = value;
	}
	
	public String getPasswd() {
		return this.passwd;
	}
	public void setHeadImg(String value) {
		this.headImg = value;
	}
	
	public String getHeadImg() {
		return this.headImg;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setFamilyId(Long value) {
		this.familyId = value;
	}
	
	public Long getFamilyId() {
		return this.familyId;
	}
	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
	
    public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserName",getUserName())		
		.append("NickName",getNickName())		
		.append("Phone",getPhone())		
		.append("Passwd",getPasswd())		
		.append("HeadImg",getHeadImg())		
		.append("GmtCreated",getGmtCreated())		
		.append("Status",getStatus())		
		.append("FamilyId",getFamilyId())		
		.append("Type",getType())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserName())
		.append(getNickName())
		.append(getPhone())
		.append(getPasswd())
		.append(getHeadImg())
		.append(getGmtCreated())
		.append(getStatus())
		.append(getFamilyId())
		.append(getType())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlUser == false) return false;
		if(this == obj) return true;
		EjlUser other = (EjlUser)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserName(),other.getUserName())

		.append(getNickName(),other.getNickName())

		.append(getPhone(),other.getPhone())

		.append(getPasswd(),other.getPasswd())

		.append(getHeadImg(),other.getHeadImg())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getStatus(),other.getStatus())

		.append(getFamilyId(),other.getFamilyId())

		.append(getType(),other.getType())

			.isEquals();
	}
}

