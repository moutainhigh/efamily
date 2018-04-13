 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class PlatformDeviceConfig extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4029673103511049441L;
	private Integer deviceType;	//设备类型
	private String connect;	//连网设置	 jsonofDeviceParamConnect
	private String common;	//普通设置	jsonofDeviceParamCommon
	private String location; //定位设置 jsonofDeviceParamLocation
	private String health;	//健康设置	jsonofDeviceParamHealth
	
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getConnect() {
		return connect;
	}
	public void setConnect(String connect) {
		this.connect = connect;
	}
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	
	
}
	

