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


public class EjlUserBarrier extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8749637886797594027L;
	//alias
	public static final String TABLE_ALIAS = "EjlUserBarrier";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_STATUS = "状态 0：关闭  1：打开 ";
	public static final String ALIAS_LOCATION = "坐标 x,y";
	public static final String ALIAS_RADIUS = "围栏半径";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private Integer status;
	private String location;
	private Long radius;
	private Integer type;
	private Integer orgTag;
	
	//columns END

	
	public EjlUserBarrier(){
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public EjlUserBarrier(
		Long id
	){
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setLocation(String value) {
		this.location = value;
	}
	
	public String getLocation() {
		return this.location;
	}
	public void setRadius(Long value) {
		this.radius = value;
	}
	
	public Long getRadius() {
		return this.radius;
	}

	public Integer getOrgTag() {
		return orgTag;
	}

	public void setOrgTag(Integer orgTag) {
		this.orgTag = orgTag;
	}

	@Override
	public String toString() {
		return "EjlUserBarrier [userId=" + userId + ", status=" + status
				+ ", location=" + location + ", radius=" + radius + ", type="
				+ type + ", orgTag=" + orgTag + "]";
	}

}

