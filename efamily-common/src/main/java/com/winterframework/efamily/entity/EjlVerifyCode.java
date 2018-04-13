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


public class EjlVerifyCode extends FmlBaseEntity {
	
	private static final long serialVersionUID = 9123793180171298719L;
	//alias
	public static final String TABLE_ALIAS = "验证码记录";
	public static final String ALIAS_FAMILY_ID = "家庭id";
	public static final String ALIAS_PHONE_NUMBER = "手机号码";
	public static final String ALIAS_IS_VALID = "是否有效 0 无效 1有效";
	public static final String ALIAS_TIME_OUT = "失效时间 （单位分钟）";
	public static final String ALIAS_USER_ID = " 创建人  -1表示系统创建";
	public static final String ALIAS_TYPE = "1 短信验证码  2 家庭邀请码";
	
	//date formats
	
	//columns START
	private Long familyId;
	private String phoneNumber;
	private Long isValid;
	private Long timeOut;
	private Long userId;
	private Long type;
	private Long status;
	private String verifyCode;
	private String messageCode;
	//columns END

	public EjlVerifyCode(){
	}

	public EjlVerifyCode(
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
	public void setPhoneNumber(String value) {
		this.phoneNumber = value;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setIsValid(Long value) {
		this.isValid = value;
	}
	
	public Long getIsValid() {
		return this.isValid;
	}
	public void setTimeOut(Long value) {
		this.timeOut = value;
	}
	
	public Long getTimeOut() {
		return this.timeOut;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
	
    public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("FamilyId",getFamilyId())		
		.append("PhoneNumber",getPhoneNumber())		
		.append("GmtCreated",getGmtCreated())		
		.append("IsValid",getIsValid())		
		.append("TimeOut",getTimeOut())		
		.append("UserId",getUserId())		
		.append("Type",getType())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getFamilyId())
		.append(getPhoneNumber())
		.append(getGmtCreated())
		.append(getIsValid())
		.append(getTimeOut())
		.append(getUserId())
		.append(getType())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlVerifyCode == false) return false;
		if(this == obj) return true;
		EjlVerifyCode other = (EjlVerifyCode)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getFamilyId(),other.getFamilyId())

		.append(getPhoneNumber(),other.getPhoneNumber())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getIsValid(),other.getIsValid())

		.append(getTimeOut(),other.getTimeOut())

		.append(getUserId(),other.getUserId())

		.append(getType(),other.getType())

			.isEquals();
	}
}

