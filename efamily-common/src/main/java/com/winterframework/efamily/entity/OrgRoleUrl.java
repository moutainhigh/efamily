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


public class OrgRoleUrl  extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "OrgRoleUrl";
	public static final String ALIAS_ROLE_ID = "角色ID";
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
	private Long roleId;
	private Long urlId;
	private Integer status;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public OrgRoleUrl(){
	}

	public OrgRoleUrl(
		Long id
	){
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUrlId() {
		return urlId;
	}

	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		return "OrgRoleUrl [roleId=" + roleId + ", urlId=" + urlId
				+ ", status=" + status + ", remark=" + remark + ", creatorId="
				+ creatorId + ", createTime=" + createTime + ", updatorId="
				+ updatorId + ", updateTime=" + updateTime + "]";
	}

}

