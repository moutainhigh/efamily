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


public class EfLocationGps extends FmlBaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "EfLocationGps";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_LOCATION = "location";
	public static final String ALIAS_DEVICE_ID = "deviceId";
	public static final String ALIAS_TIME = "time";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	public static final String ALIAS_OP_TAG = "地址是否已经解析 0否 1是";
	
	//date formats
	public static final String FORMAT_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private String location;
	private Long deviceId;
	private Date time;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	private Integer opTag;
	//columns END

	public EfLocationGps(){
	}

	public EfLocationGps(
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
	public void setLocation(String value) {
		this.location = value;
	}
	
	public String getLocation() {
		return this.location;
	}
	public void setDeviceId(Long value) {
		this.deviceId = value;
	}
	
	public Long getDeviceId() {
		return this.deviceId;
	}
	public String getTimeString() {
		return date2String(getTime(), FORMAT_TIME);
	}
	public void setTimeString(String value) {
		setTime(string2Date(value, FORMAT_TIME,Date.class));
	}
	
	public void setTime(Date value) {
		this.time = value;
	}
	
	public Date getTime() {
		return this.time;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setCreatorId(Long value) {
		this.creatorId = value;
	}
	
	public Long getCreatorId() {
		return this.creatorId;
	}
	public String getCreateTimeString() {
		return date2String(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(string2Date(value, FORMAT_CREATE_TIME,Date.class));
	}
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdatorId(Long value) {
		this.updatorId = value;
	}
	
	public Long getUpdatorId() {
		return this.updatorId;
	}
	public String getUpdateTimeString() {
		return date2String(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(string2Date(value, FORMAT_UPDATE_TIME,Date.class));
	}
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setOpTag(Integer value) {
		this.opTag = value;
	}
	
	public Integer getOpTag() {
		return this.opTag;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("Location",getLocation())		
		.append("DeviceId",getDeviceId())		
		.append("Time",getTime())		
		.append("Remark",getRemark())		
		.append("CreatorId",getCreatorId())		
		.append("CreateTime",getCreateTime())		
		.append("UpdatorId",getUpdatorId())		
		.append("UpdateTime",getUpdateTime())		
		.append("OpTag",getOpTag())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getLocation())
		.append(getDeviceId())
		.append(getTime())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
		.append(getOpTag())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EfLocationGps == false) return false;
		if(this == obj) return true;
		EfLocationGps other = (EfLocationGps)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getLocation(),other.getLocation())

		.append(getDeviceId(),other.getDeviceId())

		.append(getTime(),other.getTime())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

		.append(getOpTag(),other.getOpTag())

			.isEquals();
	}
}

