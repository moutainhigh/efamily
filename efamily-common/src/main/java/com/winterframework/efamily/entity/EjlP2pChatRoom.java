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


public class EjlP2pChatRoom extends FmlBaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "EjlP2pChatRoom";
	public static final String ALIAS_USER_ID_LITLE = "小id用户";
	public static final String ALIAS_USER_ID_BIG = "大id用户";
	
	//date formats
	
	//columns START
	private Long userIdLitle;
	private Long userIdBig;
	//columns END

	public EjlP2pChatRoom(){
	}

	public EjlP2pChatRoom(
		Long id
	){
		this.id = id;
	}

	public void setUserIdLitle(Long value) {
		this.userIdLitle = value;
	}
	
	public Long getUserIdLitle() {
		return this.userIdLitle;
	}
	public void setUserIdBig(Long value) {
		this.userIdBig = value;
	}
	
	public Long getUserIdBig() {
		return this.userIdBig;
	}
	
	
}

