 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlDeviceAddressList extends FmlBaseEntity {
	
	private static final long serialVersionUID = -8397884872624142327L;
	//alias
	public static final String TABLE_ALIAS = "手表通讯录管理表";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_HEADIMAGE = "头像";
	public static final String ALIAS_PHONENUMBER = "电话号码";
	public static final String ALIAS_USER_ID = "创建者";
	public static final String ALIAS_SOS = "是否为紧急呼叫电话";
	//date formats
	
	//columns START
	private String name;
	private String headImage;
	private String phoneNumber;
	private Long userId;
	private Long isSos;
	//columns END

	
	
	public EjlDeviceAddressList(){
	}

	public Long getIsSos() {
		return isSos;
	}

	public void setIsSos(Long isSos) {
		this.isSos = isSos;
	}



	public EjlDeviceAddressList(
		Long id
	){
		this.id = id;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "EjlDeviceAddressList [name=" + name + ", headImage="
				+ headImage + ", phoneNumber=" + phoneNumber + ", userId="
				+ userId + "]";
	}
	
	
}

