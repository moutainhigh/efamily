 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.Date;


import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlMessageMark extends FmlBaseEntity {
	
	private static final long serialVersionUID = 4671287070638846716L;
	//alias
	public static final String TABLE_ALIAS = "信息标记";
	public static final String ALIAS_MESSAGE_ID = "消息id";
	public static final String ALIAS_SEND_USER_ID = "消息发送方";
	public static final String ALIAS_RECEIVE_USER_ID = "消息接收方";
	public static final String ALIAS_SEND_TIME = "发送时间";
	public static final String ALIAS_SEND_STATUS = "发送状态 0未发送 1已发送 2 发送失败";
	public static final String ALIAS_CONTENT = "发送内容";
	public static final String ALIAS_CONTENT_TYPE = "内容类型";
	
	//date formats
			public static final String FORMAT_SEND_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long messageId;
	private Long sendUserId;
	private Long receiveUserId;
	private Date sendTime;
	private Long sendStatus;
	private String content;
	private Long contentType;
	private Long contentTime;
	private Long chatType;
	private Long chatRoomId;
	private Long preMessageId;
	private Long noReadMessageCount;
	private Long status;//  状态：0 未读，1已读，2撤销，3删除
	private Long page;
	private Long pageSize;
	
	//columns END
	
	
	public Long getPage() {
		return page;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getNoReadMessageCount() {
		return noReadMessageCount;
	}

	public void setNoReadMessageCount(Long noReadMessageCount) {
		this.noReadMessageCount = noReadMessageCount;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public EjlMessageMark(){
	}

	public EjlMessageMark(
		Long id
	){
		this.id = id;
	}

	public void setMessageId(Long value) {
		this.messageId = value;
	}
	
	public Long getMessageId() {
		return this.messageId;
	}
	public void setSendUserId(Long value) {
		this.sendUserId = value;
	}
	
	public Long getSendUserId() {
		return this.sendUserId;
	}
	public void setReceiveUserId(Long value) {
		this.receiveUserId = value;
	}
	
	public Long getReceiveUserId() {
		return this.receiveUserId;
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
	public void setSendStatus(Long value) {
		this.sendStatus = value;
	}
	
	public Long getSendStatus() {
		return this.sendStatus;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContentType(Long value) {
		this.contentType = value;
	}
	
	public Long getContentType() {
		return this.contentType;
	}
	
    public Long getContentTime() {
		return contentTime;
	}

	public void setContentTime(Long contentTime) {
		this.contentTime = contentTime;
	}

	public Long getChatType() {
		return chatType;
	}

	public void setChatType(Long chatType) {
		this.chatType = chatType;
	}

	public Long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	
	public Long getPreMessageId() {
		return preMessageId;
	}

	public void setPreMessageId(Long preMessageId) {
		this.preMessageId = preMessageId;
	}

}

