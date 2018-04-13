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


public class EjlDeviceParmConfig extends FmlBaseEntity {
	
	private static final long serialVersionUID = 1603613729339741416L;
	//alias
	public static final String TABLE_ALIAS = "设备各种参数设置表";
	public static final String ALIAS_DEVICE_ID = "设备id";
	public static final String ALIAS_PARAM_KEY = "设备参数key    规则 分模块  eg:gps_switch 开关 gps_uprate（上传频率）";
	public static final String ALIAS_PARAM_VALUE = "设备参数value";
	
	//date formats
	
	//columns START
	private Long deviceId;
	private String paramKey;
	private String paramValue;
	//columns END

	public EjlDeviceParmConfig(){
	}

	public EjlDeviceParmConfig(
		Long id
	){
		this.id = id;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public void setParamKey(String value) {
		this.paramKey = value;
	}
	
	public String getParamKey() {
		return this.paramKey;
	}
	public void setParamValue(String value) {
		this.paramValue = value;
	}
	
	public String getParamValue() {
		return this.paramValue;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("DeviceId",getDeviceId())		
		.append("ParamKey",getParamKey())		
		.append("ParamValue",getParamValue())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getDeviceId())
		.append(getParamKey())
		.append(getParamValue())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlDeviceParmConfig == false) return false;
		if(this == obj) return true;
		EjlDeviceParmConfig other = (EjlDeviceParmConfig)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getDeviceId(),other.getDeviceId())

		.append(getParamKey(),other.getParamKey())

		.append(getParamValue(),other.getParamValue())

			.isEquals();
	}
}

