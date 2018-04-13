 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;


/**
 * 第三方用户KEY
 * @ClassName
 * @Description
 * @author ibm
 * 2016年6月23日
 */
public class EfKey extends FmlBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6565263111364124359L;
	  
	private String ukey;	//客户key
	private Long customerId;	//客户ID
	
	public String getUkey() {
		return ukey;
	}
	public void setUkey(String ukey) {
		this.ukey = ukey;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
}

