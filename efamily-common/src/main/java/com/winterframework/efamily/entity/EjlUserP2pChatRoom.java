 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;



import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlUserP2pChatRoom extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "EjlUserP2pChatRoom";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_CHAT_ROOM_ID = "聊天室id";
	public static final String ALIAS_CHAT_ROOM_TOP = "是否放置聊天组到顶部：0 置顶，1不置顶";
	public static final String ALIAS_MESSAGE_NOTIFY = "群消息通知：0通知，1不通知";
	public static final String ALIAS_STATUS = "是否还在聊天室： 0还在聊天室，1离开聊天室";
	
	//columns START
	private Long userId;
	private Long chatRoomId;
	private Long chatRoomTop;
	private Long messageNotify;
	private Long status;

	//columns END

	public EjlUserP2pChatRoom(){
	}

	public EjlUserP2pChatRoom(
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
	public void setChatRoomId(Long value) {
		this.chatRoomId = value;
	}
	
	public Long getChatRoomId() {
		return this.chatRoomId;
	}
	public void setChatRoomTop(Long value) {
		this.chatRoomTop = value;
	}
	
	public Long getChatRoomTop() {
		return this.chatRoomTop;
	}
	public void setMessageNotify(Long value) {
		this.messageNotify = value;
	}
	
	public Long getMessageNotify() {
		return this.messageNotify;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	
    
}

