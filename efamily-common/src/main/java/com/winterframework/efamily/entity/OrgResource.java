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


public class OrgResource  extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "OrgResource";
	public static final String ALIAS_TYPE = "类型(image,audio.video...)";
	public static final String ALIAS_EXT_TYPE = "扩展类型(jpg,png,mp3...)";
	public static final String ALIAS_FILE_PATH = "文件路径";
	public static final String ALIAS_IS_MULTI = "是否多清度";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    private String resourceId;
	private String type;
	private String extType;
	private String filePath;
	private Integer isMulti;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	
	public OrgResource(){
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public OrgResource(
		Long id
	){
		this.id = id;
	}

	public void setType(String value) {
		this.type = value;
	}
	
	public String getType() {
		return this.type;
	}
	public void setExtType(String value) {
		this.extType = value;
	}
	
	public String getExtType() {
		return this.extType;
	}
	public void setFilePath(String value) {
		this.filePath = value;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	public void setIsMulti(Integer value) {
		this.isMulti = value;
	}
	
	public Integer getIsMulti() {
		return this.isMulti;
	}

	@Override
	public String toString() {
		return "OrgResource [resourceId=" + resourceId + ", type=" + type
				+ ", extType=" + extType + ", filePath=" + filePath
				+ ", isMulti=" + isMulti + ", remark=" + remark
				+ ", creatorId=" + creatorId + ", createTime=" + createTime
				+ ", updatorId=" + updatorId + ", updateTime=" + updateTime
				+ "]";
	}


}

