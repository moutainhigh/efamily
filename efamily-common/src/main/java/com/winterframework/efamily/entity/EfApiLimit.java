 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;


import java.util.*;



import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EfApiLimit extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "EfApiLimit";
	public static final String ALIAS_API_ID = "APIID";
	public static final String ALIAS_MIN_REQ_LIMIT = "每一分钟内请求限制";
	public static final String ALIAS_DAY_REQ_LIMIT = "一天内请求总次数限制";
 
 
	//columns START
	private Integer apiId;
	private Integer minReqLimit;
	private Integer dayReqLimit;
 
	//columns END

	public EfApiLimit(){
	}

	public EfApiLimit(
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
	 
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdatorId(Long value) {
		this.updatorId = value;
	}
	
	public Long getUpdatorId() {
		return this.updatorId;
	}

	@Override
	public String toString() {
		return "EfApiLimit [apiId=" + apiId + ", minReqLimit=" + minReqLimit
				+ ", dayReqLimit=" + dayReqLimit + ", remark=" + remark
				+ ", creatorId=" + creatorId + ", createTime=" + createTime
				+ ", updatorId=" + updatorId + ", updateTime=" + updateTime
				+ "]";
	}
   
}

