 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlMessage extends FmlBaseEntity {
	
	private static final long serialVersionUID = 102030555804502090L;
	//alias
	public static final String TABLE_ALIAS = "聊天信息管理表";
	public static final String ALIAS_SEND_USER_ID = "消息发送方";
	public static final String ALIAS_RECEIVE_USER_ID = "消息接收方";
	public static final String ALIAS_CONTENT = "消息内容";
	public static final String ALIAS_CONTENT_TYPE = "内容类型 1 文字 2 音频 3 视频 4 图片";
	public static final String ALIAS_CHAT_TYPE = "聊天类型 ：1 对手表发消息 2 点对点 即app 对app  3 群聊（不包含手表）";
	public static final String ALIAS_CHAT_ROOM_ID = " 群聊聊天室id 当不为群聊时，此字段值为0 数据库设计约定所有字段为必填";
	
	//date formats
	
	//columns START
	private Long sendUserId;
	private Long receiveUserId;
	private String content;
	private Long contentType;
	private Long chatType;
	private Long chatRoomId;
	private Long contentTime;
	private String senderName;	//发送者昵称
	private Integer status;
	private Long appSendTime; //客户端发送时间
	//columns END

	public enum ContentType { 
		TEXT(1,ResourceType.TEXT),	
		AUDIO(2,ResourceType.AUDIO),
		VIDEO(3,ResourceType.VIDEO),
		IMAGE(4,ResourceType.IMAGE),
		CLIENT(5,ResourceType.CLIENT),
		TIPS(6,ResourceType.TIPS);
		
		private int _code;
		private String _value;
		ContentType(int code,String value){
			this._code=code;
			this._value=value;
		}
		public int getCode(){
			return _code;
		}
		public String getValue(){
			return _value;
		}
		public static ContentType get(String value){
			for(ContentType param:ContentType.values()){
				if(value.equals(param.getValue())){
					return param;
				}
			}
			return null;
		}
		public static ContentType get(int code){
			for(ContentType param:ContentType.values()){
				if(code==param.getCode()){
					return param;
				}
			}
			return null;
		}
	}
	public enum ChatType { 
		FML(1),	
		P2P(2),
		GROUP(3),
		FML_GROUP(4);
		private int _value;
		ChatType(int value){
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}
	public EjlMessage(){
	}

	public EjlMessage(
		Long id
	){
		this.id = id;
	}

	
	public Long getAppSendTime() {
		return appSendTime;
	}

	public void setAppSendTime(Long appSendTime) {
		this.appSendTime = appSendTime;
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
	public void setChatType(Long value) {
		this.chatType = value;
	}
	
	public Long getChatType() {
		return this.chatType;
	}
	public void setChatRoomId(Long value) {
		this.chatRoomId = value;
	}
	
	public Long getChatRoomId() {
		return this.chatRoomId;
	}
	
    public Long getContentTime() {
		return contentTime;
	}

	public void setContentTime(Long contentTime) {
		this.contentTime = contentTime;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("SendUserId",getSendUserId())
		.append("appSendTime",getAppSendTime())
		.append("ReceiveUserId",getReceiveUserId())		
		.append("Content",getContent())		
		.append("ContentType",getContentType())		
		.append("GmtCreated",getGmtCreated())		
		.append("ChatType",getChatType())
		.append("Status",getStatus())
		.append("ChatRoomId",getChatRoomId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getSendUserId())
		.append(getAppSendTime())
		.append(getReceiveUserId())
		.append(getContent())
		.append(getContentType())
		.append(getGmtCreated())
		.append(getChatType())
		.append(getChatRoomId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlMessage == false) return false;
		if(this == obj) return true;
		EjlMessage other = (EjlMessage)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getSendUserId(),other.getSendUserId())
		
		.append(getAppSendTime(),other.getAppSendTime())

		.append(getReceiveUserId(),other.getReceiveUserId())

		.append(getContent(),other.getContent())

		.append(getContentType(),other.getContentType())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getChatType(),other.getChatType())

		.append(getChatRoomId(),other.getChatRoomId())

			.isEquals();
	}
}

