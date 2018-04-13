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


public class OrgUserFamilyDisease  extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "OrgEmployeeFamilyDisease";
	public static final String ALIAS_ORG_ID = "资源ID";
	public static final String ALIAS_EMPLOYEE_ID = "员工ID";
	public static final String ALIAS_STATUS = "状态 : 0 不可用  1可用";
	public static final String ALIAS_RELATION = "关系";
	public static final String ALIAS_DISEASE = "疾病编码";
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
	private Long orgUserId;
	private Integer status;
	private String relation;
	private String disease;

	//columns END

	public OrgUserFamilyDisease(){
	}

	public OrgUserFamilyDisease(
		Long id
	){
		this.id = id;
	}



	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgUserId() {
		return orgUserId;
	}

	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getTableAlias() {
		return TABLE_ALIAS;
	}

	public static String getAliasOrgId() {
		return ALIAS_ORG_ID;
	}

	public static String getAliasEmployeeId() {
		return ALIAS_EMPLOYEE_ID;
	}

	public static String getAliasStatus() {
		return ALIAS_STATUS;
	}

	public static String getAliasRelation() {
		return ALIAS_RELATION;
	}

	public static String getAliasDisease() {
		return ALIAS_DISEASE;
	}

	public static String getAliasRemark() {
		return ALIAS_REMARK;
	}

	public static String getAliasCreatorId() {
		return ALIAS_CREATOR_ID;
	}

	public static String getAliasCreateTime() {
		return ALIAS_CREATE_TIME;
	}

	public static String getAliasUpdatorId() {
		return ALIAS_UPDATOR_ID;
	}

	public static String getAliasUpdateTime() {
		return ALIAS_UPDATE_TIME;
	}

	public static String getFormatCreateTime() {
		return FORMAT_CREATE_TIME;
	}

	public static String getFormatUpdateTime() {
		return FORMAT_UPDATE_TIME;
	}

	@Override
	public String toString() {
		return "OrgEmployeeFamilyDisease [orgId=" + orgId + ", orgUserId="
				+ orgUserId + ", status=" + status + ", relation=" + relation
				+ ", disease=" + disease + "]";
	}

}

