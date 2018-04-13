 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.Date;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class EfOperLog extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9011881416860363420L;
	
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private String operation;	//操作
	private Date time;	//操作时间
	private String command;
	
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
}
	

