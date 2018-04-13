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


public class OrgEmployeeAuth extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "OrgEmployeeAuth";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_ORG_EMPLOYEE_ID = "员工ID";
	public static final String ALIAS_STATUS = "状态 : 0 不可以查看  1可以查看";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long orgId;
	private Long orgEmployeeId;
	private Integer status;
	//columns END

	public OrgEmployeeAuth(){
	}

	public OrgEmployeeAuth(
		Long id
	){
		this.id = id;
	}

	public void setOrgId(Long value) {
		this.orgId = value;
	}
	
	public Long getOrgId() {
		return this.orgId;
	}
	public void setOrgEmployeeId(Long value) {
		this.orgEmployeeId = value;
	}
	
	public Long getOrgEmployeeId() {
		return this.orgEmployeeId;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		return "OrgEmployeeAuth [orgId=" + orgId + ", orgEmployeeId="
				+ orgEmployeeId + ", status=" + status + "]";
	}
	
}

