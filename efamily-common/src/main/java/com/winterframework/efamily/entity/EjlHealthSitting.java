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


public class EjlHealthSitting extends  FmlBaseEntity {
	private static final long serialVersionUID = -8013145634538343858L;
	//alias
	public static final String TABLE_ALIAS = "EjlHealthSitting";
	public static final String ALIAS_USER_ID = "用户Id";
	public static final String ALIAS_DEVICE_ID = "设备Id";
	public static final String ALIAS_START_TIME = "久坐开始时间";
	public static final String ALIAS_END_TIME = "久坐结束时间";

	
	//date formats
			public static final String FORMAT_START_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_END_TIME = DATE_TIME_FORMAT;

	
	//columns START
	private Long userId;
	private Long deviceId;
	private Date startTime;
	private Date endTime;
	
	//columns END

	public EjlHealthSitting(){
	}

	public EjlHealthSitting(
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
	public String getStartTimeString() {
		return date2String(getStartTime(), FORMAT_START_TIME);
	}
	public void setStartTimeString(String value) {
		setStartTime(string2Date(value, FORMAT_START_TIME,Date.class));
	}
	
	public void setStartTime(Date value) {
		this.startTime = value;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	public String getEndTimeString() {
		return date2String(getEndTime(), FORMAT_END_TIME);
	}
	public void setEndTimeString(String value) {
		setEndTime(string2Date(value, FORMAT_END_TIME,Date.class));
	}
	
	public void setEndTime(Date value) {
		this.endTime = value;
	}
	
	public Date getEndTime() {
		return this.endTime;
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
	
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("DeviceId",getDeviceId())		
		.append("StartTime",getStartTime())		
		.append("EndTime",getEndTime())			
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getDeviceId())
		.append(getStartTime())
		.append(getEndTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlHealthSitting == false) return false;
		if(this == obj) return true;
		EjlHealthSitting other = (EjlHealthSitting)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getDeviceId(),other.getDeviceId())

		.append(getStartTime(),other.getStartTime())

		.append(getEndTime(),other.getEndTime())

			.isEquals();
	}
}

