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


public class EfCustomer extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8974215644938245229L;
	//alias
	public static final String TABLE_ALIAS = "EfCustomer";
	public static final String ALIAS_NAME = "客户名称";
	public static final String ALIAS_SERVICE_TEL = "服务电话";
	public static final String ALIAS_LOGO_URL = "首页URL";
	public static final String ALIAS_WE_CHAT = "微信";
	public static final String ALIAS_WEIBO_NAME = "微博名称";
	public static final String ALIAS_WEIBO_URL = "微博URL";
	public static final String ALIAS_QRCODE_URL = "二维码URL";
	public static final String ALIAS_DOWNLOAD_URL = "APP下载URL";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private String name;
	private Integer number;
	private String serviceTel;
	private String logoUrl;
	private String weChat;
	private String weiboName;
	private String weiboUrl;
	private String qrcodeUrl;
	private String downloadUrl;
	private String timezone;
	//columns END

	
	public EfCustomer(){
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public EfCustomer(
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
	public void setServiceTel(String value) {
		this.serviceTel = value;
	}
	
	public String getServiceTel() {
		return this.serviceTel;
	}
	public void setLogoUrl(String value) {
		this.logoUrl = value;
	}
	
	public String getLogoUrl() {
		return this.logoUrl;
	}
	public void setWeChat(String value) {
		this.weChat = value;
	}
	
	public String getWeChat() {
		return this.weChat;
	}
	public void setWeiboName(String value) {
		this.weiboName = value;
	}
	
	public String getWeiboName() {
		return this.weiboName;
	}
	public void setWeiboUrl(String value) {
		this.weiboUrl = value;
	}
	
	public String getWeiboUrl() {
		return this.weiboUrl;
	}
	public void setQrcodeUrl(String value) {
		this.qrcodeUrl = value;
	}
	
	public String getQrcodeUrl() {
		return this.qrcodeUrl;
	}
	public void setDownloadUrl(String value) {
		this.downloadUrl = value;
	}
	
	public String getDownloadUrl() {
		return this.downloadUrl;
	}
	
	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@Override
	public String toString() {
		return "EfCustomer [name=" + name + ", number=" + number
				+ ", serviceTel=" + serviceTel + ", logoUrl=" + logoUrl
				+ ", weChat=" + weChat + ", weiboName=" + weiboName
				+ ", weiboUrl=" + weiboUrl + ", qrcodeUrl=" + qrcodeUrl
				+ ", downloadUrl=" + downloadUrl + "]";
	}
	
}

