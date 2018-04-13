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


public class EfKeyApiLimit  extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_ALIAS = "EfKeyApiLimit";
	public static final String ALIAS_API_ID = "APIID";
	public static final String ALIAS_UKEY = "客户key";
	public static final String ALIAS_MIN_REQ_LIMIT = "每一分钟内请求限制";
	public static final String ALIAS_DAY_REQ_LIMIT = "一天内请求总次数限制";
	
	//columns START
	private Integer apiId;
	private String ukey;
	private Integer minReqLimit;
	private Integer dayReqLimit;
 
	//columns END

	public EfKeyApiLimit(){
	}

	public EfKeyApiLimit(
		Long id
	){
		this.id = id;
	}

	public void setApiId(Integer value) {
		this.apiId = value;
	}
	
	public Integer getApiId() {
		return this.apiId;
	}
	public void setUkey(String value) {
		this.ukey = value;
	}
	
	public String getUkey() {
		return this.ukey;
	}
	public void setMinReqLimit(Integer value) {
		this.minReqLimit = value;
	}
	
	public Integer getMinReqLimit() {
		return this.minReqLimit;
	}
	public void setDayReqLimit(Integer value) {
		this.dayReqLimit = value;
	}
	
	public Integer getDayReqLimit() {
		return this.dayReqLimit;
	}
	 
	@Override
	public String toString() {
		return "EfKeyApiLimit [apiId=" + apiId + ", ukey=" + ukey
				+ ", minReqLimit=" + minReqLimit + ", dayReqLimit="
				+ dayReqLimit + ", remark=" + remark + ", creatorId="
				+ creatorId + ", createTime=" + createTime + ", updatorId="
				+ updatorId + ", updateTime=" + updateTime + "]";
	}
   
}

