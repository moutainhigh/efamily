 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;


/**
 * 第三方用户API列表
 * @ClassName
 * @Description
 * @author ibm
 * 2016年6月23日
 */
public class EfKeyApi extends FmlBaseEntity{

	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024333670816799205L;
	
	private String ukey;	//客户key
	private String apiIds;	//客户ID
	
	public String getUkey() {
		return ukey;
	}
	public void setUkey(String key) {
		this.ukey = key;
	}
	public String getApiIds() {
		return apiIds;
	}
	public void setApiIds(String apiIds) {
		this.apiIds = apiIds;
	}
	
}

