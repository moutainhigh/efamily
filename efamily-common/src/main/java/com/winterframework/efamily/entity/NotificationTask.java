 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class NotificationTask extends FmlBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8047680744524062616L;
	private Integer notyType;	//消息类型
	private Long messageId;	//消息ID 
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private Integer status;	//状态(0:未发送 1:已发送 2:已送达 7:已删除)
	
	public Integer getNotyType() {
		return notyType;
	}
	public void setNotyType(Integer notyType) {
		this.notyType = notyType;
	}
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
}
	

