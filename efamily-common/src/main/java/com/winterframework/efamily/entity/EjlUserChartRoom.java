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


public class EjlUserChartRoom extends FmlBaseEntity {
	
	private static final long serialVersionUID = 8555828493397741803L;
	//alias
	public static final String TABLE_ALIAS = "用户保留的群聊列表";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_CHAT_ROOM_ID = "聊天室id";
	
	//date formats
	
	//columns START
	private Long userId;
	private Long chatRoomId;
	
	private String chatRoomName;
	private Long chatRoomTop;
	private Long messageNotify;
	private Long saveAddressBook;
	private Long status ;
	//columns END

	public EjlUserChartRoom(){
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	

	public Long getChatRoomTop() {
		return chatRoomTop;
	}

	public void setChatRoomTop(Long chatRoomTop) {
		this.chatRoomTop = chatRoomTop;
	}

	public Long getMessageNotify() {
		return messageNotify;
	}

	public void setMessageNotify(Long messageNotify) {
		this.messageNotify = messageNotify;
	}

	public Long getSaveAddressBook() {
		return saveAddressBook;
	}

	public void setSaveAddressBook(Long saveAddressBook) {
		this.saveAddressBook = saveAddressBook;
	}

	public EjlUserChartRoom(
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

	public String getChatRoomName() {
		return chatRoomName;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
    
}

