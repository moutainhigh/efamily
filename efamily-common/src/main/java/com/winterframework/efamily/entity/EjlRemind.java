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


public class EjlRemind extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8400046152307439971L;
	//alias
	public static final String TABLE_ALIAS = "提醒";
	public static final String ALIAS_NAME = "提醒名称";
	public static final String ALIAS_USER_ID = "创建人";
	public static final String ALIAS_CONTENT = "提醒内容url，提醒是语音因此保存url地址";
	public static final String ALIAS_SEND_TIME = "提醒时间";
	public static final String ALIAS_SEND_USER_ID = "提醒人";
	public static final String ALIAS_SEND_METHOD = "提醒方式 0 本机app 1邮件 2 手机 3 其他方式";
	public static final String ALIAS_SENT_STATUS = "发送提醒状态 0 未发送 1 已发送";
	
	//date formats
			public static final String FORMAT_SEND_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private String name;
	private Long userId;
	private String content;
	private Date sendTime;
	private Long sendUserId;
	private Long sendMethod;
	private Long sentStatus;
	private Long deleteStatus;
	private Long sendPeriod;
	private Date sendTimeEnd;
	private Long sendType;
	private Long remindState;
	private Date receiveTime;
	private Long durationTime;
	private Long deviceCommond;
	private Long remindId;
	//columns END

	public EjlRemind(){
	}

	public EjlRemind(
		Long id
	){
		this.id = id;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	public String getSendTimeString() {
		return date2String(getSendTime(), FORMAT_SEND_TIME);
	}
	public void setSendTimeString(String value) {
		setSendTime(string2Date(value, FORMAT_SEND_TIME,Date.class));
	}
	
	public void setSendTime(Date value) {
		this.sendTime = value;
	}
	
	public Date getSendTime() {
		return this.sendTime;
	}
	public void setSendUserId(Long value) {
		this.sendUserId = value;
	}
	
	public Long getSendUserId() {
		return this.sendUserId;
	}
	public void setSendMethod(Long value) {
		this.sendMethod = value;
	}
	
	public Long getSendMethod() {
		return this.sendMethod;
	}
	public void setSentStatus(Long value) {
		this.sentStatus = value;
	}
	
	public Long getSentStatus() {
		return this.sentStatus;
	}
	
	public Long getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Long deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Long getSendPeriod() {
		return sendPeriod;
	}

	public void setSendPeriod(Long sendPeriod) {
		this.sendPeriod = sendPeriod;
	}

	public Date getSendTimeEnd() {
		return sendTimeEnd;
	}

	public void setSendTimeEnd(Date sendTimeEnd) {
		this.sendTimeEnd = sendTimeEnd;
	}

	public Long getSendType() {
		return sendType;
	}

	public void setSendType(Long sendType) {
		this.sendType = sendType;
	}

	public Long getRemindState() {
		return remindState;
	}

	public void setRemindState(Long remindState) {
		this.remindState = remindState;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	public Long getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Long durationTime) {
		this.durationTime = durationTime;
	}

	public Long getDeviceCommond() {
		return deviceCommond;
	}

	public void setDeviceCommond(Long deviceCommond) {
		this.deviceCommond = deviceCommond;
	}

	public Long getRemindId() {
		return remindId;
	}

	public void setRemindId(Long remindId) {
		this.remindId = remindId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
		.append("GmtCreated",getGmtCreated())		
		.append("UserId",getUserId())		
		.append("Content",getContent())		
		.append("SendTime",getSendTime())		
		.append("SendUserId",getSendUserId())		
		.append("SendMethod",getSendMethod())		
		.append("SentStatus",getSentStatus())		
		.append("IsDeleted",getIsDeleted())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getName())
		.append(getGmtCreated())
		.append(getUserId())
		.append(getContent())
		.append(getSendTime())
		.append(getSendUserId())
		.append(getSendMethod())
		.append(getSentStatus())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlRemind == false) return false;
		if(this == obj) return true;
		EjlRemind other = (EjlRemind)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getUserId(),other.getUserId())

		.append(getContent(),other.getContent())

		.append(getSendTime(),other.getSendTime())

		.append(getSendUserId(),other.getSendUserId())

		.append(getSendMethod(),other.getSendMethod())

		.append(getSentStatus(),other.getSentStatus())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

