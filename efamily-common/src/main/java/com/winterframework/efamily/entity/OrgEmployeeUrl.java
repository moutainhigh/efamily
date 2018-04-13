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


public class OrgEmployeeUrl  extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "OrgEmployeeUrl";
	public static final String ALIAS_EMPLOYEE_ID = "员工ID";
	public static final String ALIAS_URL_ID = "资源ID";
	public static final String ALIAS_STATUS = "状态 : 0 不可用  1可用";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Integer employeeId;
	private Integer urlId;
	private Integer status;

	//columns END

	public OrgEmployeeUrl(){
	}

	public OrgEmployeeUrl(
		Long id
	){
		this.id = id;
	}

	public void setEmployeeId(Integer value) {
		this.employeeId = value;
	}
	
	public Integer getEmployeeId() {
		return this.employeeId;
	}
	public void setUrlId(Integer value) {
		this.urlId = value;
	}
	
	public Integer getUrlId() {
		return this.urlId;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		return "OrgEmployeeUrl [employeeId=" + employeeId + ", urlId=" + urlId
				+ ", status=" + status + "]";
	}

}

