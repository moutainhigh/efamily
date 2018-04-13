 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class EfPlatformDeviceSetting extends FmlBaseEntity {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5966304989144095146L;
	private Integer deviceType;	//设备类型	同EjlDevice.type
	private String connect;	//连网设置	 jsonofDeviceParamConnect
	private String common;	//普通设置	jsonofDeviceParamCommon
	private String frequency; //频率设置 jsonofDeviceParamFrequency
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
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	} 
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	
	
}
	

