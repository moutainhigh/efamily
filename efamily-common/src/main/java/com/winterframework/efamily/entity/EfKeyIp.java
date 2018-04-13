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


public class EfKeyIp extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_ALIAS = "EfKeyIp";
	public static final String ALIAS_UKEY = "客户key";
	public static final String ALIAS_IP = "客户访问的IP";
 
 
	//columns START
	private String ukey;
	private String ip;
 
	//columns END

	public EfKeyIp(){
	}

	public EfKeyIp(
		Long id
	){
		this.id = id;
	}

	public void setUkey(String value) {
		this.ukey = value;
	}
	
	public String getUkey() {
		return this.ukey;
	}
	public void setIp(String value) {
		this.ip = value;
	}
	
	public String getIp() {
		return this.ip;
	}

	@Override
	public String toString() {
		return "EfKeyIp [ukey=" + ukey + ", ip=" + ip + "]";
	}
	 
}

