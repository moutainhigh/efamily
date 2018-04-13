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


public class EfCommandInfo  extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9091728798514763898L;
	//alias
	public static final String TABLE_ALIAS = "EfCommandInfo";
	public static final String ALIAS_COMMAND = "操作协议号";
	public static final String ALIAS_OPERATION = "操作名称";
	public static final String ALIAS_URL = "请求URL";
	
	//columns START
	private String command;
	private String operation;
	private String url;

	//columns END

	public EfCommandInfo(){
	}

	public EfCommandInfo(
		Long id
	){
		this.id = id;
	}

	public void setCommand(String value) {
		this.command = value;
	}
	
	public String getCommand() {
		return this.command;
	}
	public void setOperation(String value) {
		this.operation = value;
	}
	
	public String getOperation() {
		return this.operation;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	
    
}

