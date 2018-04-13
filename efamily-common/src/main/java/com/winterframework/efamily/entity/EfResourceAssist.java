 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.Date;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EfResourceAssist extends FmlBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -482300365064060790L;
	//alias
	public static final String TABLE_ALIAS = "EfResourceAssist";
	public static final String ALIAS_RESOURCE_ID = "资源ID";
	public static final String ALIAS_TYPE = "类型： 1 默认头像 (headImange...)";
	public static final String ALIAS_STATUS = "状态：0 可用，1 禁用";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private String resourceId;
	private Integer type;
	private Integer status;
	private String remark;
	private Date createTime;
	//columns END

	public EfResourceAssist(){
	}

	public EfResourceAssist(
		Long id
	){
		this.id = id;
	}

	public void setResourceId(String value) {
		this.resourceId = value;
	}
	
	public String getResourceId() {
		return this.resourceId;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public String getCreateTimeString() {
		return date2String(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(string2Date(value, FORMAT_CREATE_TIME,Date.class));
	}
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
    
}

