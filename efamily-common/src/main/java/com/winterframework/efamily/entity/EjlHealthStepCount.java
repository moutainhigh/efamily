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


public class EjlHealthStepCount extends FmlBaseEntity {
	

	private static final long serialVersionUID = -8013810647638343858L;
	//alias
	public static final String TABLE_ALIAS = "健康计步";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_STEPS = "步数";
	public static final String ALIAS_BEGINTIME = "计步起始时间";
	public static final String ALIAS_ENDTIME = "计步结束时间";
	public static final String ALIAS_DEVICE_ID = "设备id";
	
	//date formats
			public static final String FORMAT_BEGINTIME = DATE_TIME_FORMAT;
			public static final String FORMAT_ENDTIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private Long steps;
	private Date begintime;
	private Date endtime;
	private Long deviceId;
	private Integer type;
	private Long calorie;
	private Long height;
	
	private Date startQueryTime;
	private Date endQueryTime;
	private Integer monitorDataType;
	//columns END

	public EjlHealthStepCount(){
	}

	public EjlHealthStepCount(
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
	public void setSteps(Long value) {
		this.steps = value;
	}
	
	public Long getSteps() {
		return this.steps;
	}
	public String getBegintimeString() {
		return date2String(getBegintime(), FORMAT_BEGINTIME);
	}
	public void setBegintimeString(String value) {
		setBegintime(string2Date(value, FORMAT_BEGINTIME,Date.class));
	}
	
	public void setBegintime(Date value) {
		this.begintime = value;
	}
	
	public Date getBegintime() {
		return this.begintime;
	}
	public String getEndtimeString() {
		return date2String(getEndtime(), FORMAT_ENDTIME);
	}
	public void setEndtimeString(String value) {
		setEndtime(string2Date(value, FORMAT_ENDTIME,Date.class));
	}
	
	public void setEndtime(Date value) {
		this.endtime = value;
	}
	
	public Date getEndtime() {
		return this.endtime;
	}
	public void setDeviceId(Long value) {
		this.deviceId = value;
	}
	
	public Long getDeviceId() {
		return this.deviceId;
	}
	
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCalorie() {
		return calorie;
	}

	public void setCalorie(Long calorie) {
		this.calorie = calorie;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	
	public Date getStartQueryTime() {
		return startQueryTime;
	}

	public void setStartQueryTime(Date startQueryTime) {
		this.startQueryTime = startQueryTime;
	}

	public Date getEndQueryTime() {
		return endQueryTime;
	}

	public void setEndQueryTime(Date endQueryTime) {
		this.endQueryTime = endQueryTime;
	}

	public Integer getMonitorDataType() {
		return monitorDataType;
	}

	public void setMonitorDataType(Integer monitorDataType) {
		this.monitorDataType = monitorDataType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("GmtCreated",getGmtCreated())		
		.append("Steps",getSteps())		
		.append("Begintime",getBegintime())		
		.append("Endtime",getEndtime())		
		.append("DeviceId",getDeviceId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getGmtCreated())
		.append(getSteps())
		.append(getBegintime())
		.append(getEndtime())
		.append(getDeviceId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlHealthStepCount == false) return false;
		if(this == obj) return true;
		EjlHealthStepCount other = (EjlHealthStepCount)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getSteps(),other.getSteps())

		.append(getBegintime(),other.getBegintime())

		.append(getEndtime(),other.getEndtime())

		.append(getDeviceId(),other.getDeviceId())

			.isEquals();
	}
}

