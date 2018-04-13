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


public class EjlUserDevice extends FmlBaseEntity {
	
	private static final long serialVersionUID = 2254859044377168588L;
	//alias
	public static final String TABLE_ALIAS = "用户设备记录表";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_DEVICE_ID = "设备id";
	public static final String ALIAS_STATUS = "状态 0 已停用（切换，换设备等） 1 使用中";
	
	//date formats
	
	//columns START
	private Long userId;
	private Long deviceId;
	private Long status;
	private Long optBindUserId  ;
	//columns END

	
	public EjlUserDevice(){
	}

	public Long getOptBindUserId() {
		return optBindUserId;
	}

	public void setOptBindUserId(Long optBindUserId) {
		this.optBindUserId = optBindUserId;
	}

	public EjlUserDevice(
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
	public void setDeviceId(Long value) {
		this.deviceId = value;
	}
	
	public Long getDeviceId() {
		return this.deviceId;
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
		.append("DeviceId",getDeviceId())		
		.append("Status",getStatus())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getDeviceId())
		.append(getStatus())
		.append(getGmtCreated())
		.append(getGmtModified())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlUserDevice == false) return false;
		if(this == obj) return true;
		EjlUserDevice other = (EjlUserDevice)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getDeviceId(),other.getDeviceId())

		.append(getStatus(),other.getStatus())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

			.isEquals();
	}
}

