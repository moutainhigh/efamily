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


public class OrgUrl   extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "OrgUrl";
	public static final String ALIAS_PARENT_ID = "父级ID";
	public static final String ALIAS_NUMBER = "编号";
	public static final String ALIAS_NAME = "资源名称";
	public static final String ALIAS_URL = "url";
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
	private Integer parentId;
	private Integer number;
	private String name;
	private String url;
	private Integer status;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public OrgUrl(){
	}

	public OrgUrl(
		Long id
	){
		this.id = id;
	}

	public void setParentId(Integer value) {
		this.parentId = value;
	}
	
	public Integer getParentId() {
		return this.parentId;
	}
	public void setNumber(Integer value) {
		this.number = value;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		return "OrgUrl [parentId=" + parentId + ", number=" + number
				+ ", name=" + name + ", url=" + url + ", status=" + status
				+ ", remark=" + remark + ", creatorId=" + creatorId
				+ ", createTime=" + createTime + ", updatorId=" + updatorId
				+ ", updateTime=" + updateTime + "]";
	}

}

