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


public class EjlHealthHeartRate extends FmlBaseEntity {
	
	private static final long serialVersionUID = -3384018554801548208L;
	//alias
	public static final String TABLE_ALIAS = "健康心率表";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_RATE = "心率";
	public static final String ALIAS_TIME_SPAN = "时间间隔（单位：s）";
	public static final String ALIAS_DEVICE_ID = "设备id";
	
	//date formats
	
	//columns START
	private Long userId;
	private Long rate;
	private Long timeSpan;
	private Long deviceId;
	private Integer opTag;
	private Long fromTime;
	private Long toTime;
	//columns END

	public enum MeasureStatus {
		// -1不可用0成功1忙中2未正确佩戴
		NA(-1),
		SUCCESS(0),
		BUSING(1),
		INCORRECT(2);
		
		private int _value;
		MeasureStatus(int v){
			_value=v;
		}
		public int getValue() {
			return _value;
		}
		public void setValue(int _value) {
			this._value = _value;
		}
		
	}
	public EjlHealthHeartRate(){
	}

	public EjlHealthHeartRate(
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
	public void setRate(Long value) {
		this.rate = value;
	}
	
	public Long getRate() {
		return this.rate;
	}
	public void setTimeSpan(Long value) {
		this.timeSpan = value;
	}
	
	public Long getTimeSpan() {
		return this.timeSpan;
	}
	public void setDeviceId(Long value) {
		this.deviceId = value;
	}
	
	public Long getDeviceId() {
		return this.deviceId;
	}
	
    public Integer getOpTag() {
		return opTag;
	}

	public void setOpTag(Integer opTag) {
		this.opTag = opTag;
	}

	public Long getFromTime() {
		return fromTime;
	}

	public void setFromTime(Long fromTime) {
		this.fromTime = fromTime;
	}

	public Long getToTime() {
		return toTime;
	}

	public void setToTime(Long toTime) {
		this.toTime = toTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("GmtCreated",getGmtCreated())		
		.append("Rate",getRate())		
		.append("TimeSpan",getTimeSpan())		
		.append("DeviceId",getDeviceId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getGmtCreated())
		.append(getRate())
		.append(getTimeSpan())
		.append(getDeviceId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlHealthHeartRate == false) return false;
		if(this == obj) return true;
		EjlHealthHeartRate other = (EjlHealthHeartRate)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getRate(),other.getRate())

		.append(getTimeSpan(),other.getTimeSpan())

		.append(getDeviceId(),other.getDeviceId())

			.isEquals();
	}
}

