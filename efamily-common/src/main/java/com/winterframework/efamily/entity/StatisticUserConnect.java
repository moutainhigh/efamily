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


public class StatisticUserConnect extends FmlBaseEntity {
	
	private static final long serialVersionUID = 1214673114182551141L;
	//alias
	public static final String TABLE_ALIAS = "StatisticUserConnect";
	public static final String ALIAS_APPCOUNT = "appcount";
	public static final String ALIAS_TIME = "time";
	public static final String ALIAS_DEVICECOUNT = "devicecount";
	
	//date formats
	public static final String FORMAT_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Integer appcount;
	private Date time;
	private Integer devicecount;
	//columns END
	private String ip;
	
	


	public StatisticUserConnect(){
	}

	public StatisticUserConnect(
		Long id
	){
		this.id = id;
	}

	public void setAppcount(Integer value) {
		this.appcount = value;
	}
	
	public Integer getAppcount() {
		return this.appcount;
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
	public void setDevicecount(Integer value) {
		this.devicecount = value;
	}
	
	public Integer getDevicecount() {
		return this.devicecount;
	}
	
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Appcount",getAppcount())		
		.append("Time",getTime())		
		.append("Devicecount",getDevicecount())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getAppcount())
		.append(getTime())
		.append(getDevicecount())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof StatisticUserConnect == false) return false;
		if(this == obj) return true;
		StatisticUserConnect other = (StatisticUserConnect)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getAppcount(),other.getAppcount())

		.append(getTime(),other.getTime())

		.append(getDevicecount(),other.getDevicecount())

			.isEquals();
	}
}

