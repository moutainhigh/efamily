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


public class EjlDeviceMonitor extends FmlBaseEntity {
	private static final long serialVersionUID = -8013810634538343858L;
	//alias
	public static final String TABLE_ALIAS = "用户设备监控";
	public static final String ALIAS_DEVICE_USER_ID = "被监听人";
	public static final String ALIAS_USER_ID = "监听人";
	public static final String ALIAS_START_TIME = "监听开始时间";
	public static final String ALIAS_END_TIME = "监听结束时间";
	public static final String ALIAS_DEVICE_ID = "被监听的设备";
	public static final String ALIAS_STATUS = "0：未结束，1：已结束";

	
	//date formats
			public static final String FORMAT_START_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_END_TIME = DATE_TIME_FORMAT;

	
	//columns START
	private Long deviceUserId;
	private Long userId;
	private Date startTime;
	private Date endTime;
	private Long deviceId;
	private Integer status;
	
	//columns END

	public EjlDeviceMonitor(){
	}

	public EjlDeviceMonitor(
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
	public void setDeviceId(Long value) {
		this.deviceId = value;
	}
	
	public Long getDeviceId() {
		return this.deviceId;
	}
	
    public Long getDeviceUserId() {
		return deviceUserId;
	}

	public void setDeviceUserId(Long deviceUserId) {
		this.deviceUserId = deviceUserId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("DeviceUserId",getDeviceUserId())		
		.append("UserId",getUserId())		
		.append("StartTime",getStartTime())		
		.append("EndTime",getEndTime())		
		.append("DeviceId",getDeviceId())
		.append("Status",getStatus())
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getDeviceUserId())
		.append(getUserId())
		.append(getStartTime())
		.append(getEndTime())
		.append(getDeviceId())
		.append(getStatus())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlDeviceMonitor == false) return false;
		if(this == obj) return true;
		EjlDeviceMonitor other = (EjlDeviceMonitor)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getDeviceUserId(),other.getDeviceUserId())

		.append(getUserId(),other.getUserId())

		.append(getStartTime(),other.getStartTime())

		.append(getEndTime(),other.getEndTime())

		.append(getDeviceId(),other.getDeviceId())
		
		.append(getStatus(), other.getStatus())

			.isEquals();
	}
}

