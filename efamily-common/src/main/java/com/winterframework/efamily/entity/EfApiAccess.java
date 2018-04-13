 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EfApiAccess extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4645454545787l;
	//alias
	public static final String TABLE_ALIAS = "EfApiAccess";
	public static final String ALIAS_API_ID = "APIID";
	public static final String ALIAS_U_KEY = "访问标示";
	public static final String ALIAS_COUNT = "次数";
	public static final String ALIAS_TIME = "time";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	
	//date formats
			public static final String FORMAT_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long apiId;
	private String ukey;
	private Integer count;
	private Date time;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public EfApiAccess(){
	}

	public EfApiAccess(
		Long id
	){
		this.id = id;
	}

	public void setApiId(Long value) {
		this.apiId = value;
	}
	
	public Long getApiId() {
		return this.apiId;
	}
	
	
	
	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}

	public void setCount(Integer value) {
		this.count = value;
	}
	
	public Integer getCount() {
		return this.count;
	}
	public String getTimeString() {
		return date2String(getTime(), FORMAT_TIME);
	}
	public void setTimeString(String value) {
		setTime(string2Date(value, FORMAT_TIME,Date.class));
	}
	
	public void setTime(Date value) {
		this.time = value;
	}
	
	public Date getTime() {
		return this.time;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setCreatorId(Long value) {
		this.creatorId = value;
	}
	
	public Long getCreatorId() {
		return this.creatorId;
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
	public void setUpdatorId(Long value) {
		this.updatorId = value;
	}
	
	public Long getUpdatorId() {
		return this.updatorId;
	}
	public String getUpdateTimeString() {
		return date2String(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(string2Date(value, FORMAT_UPDATE_TIME,Date.class));
	}
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("ApiId",getApiId())		
		.append("key",getUkey())		
		.append("Count",getCount())		
		.append("Time",getTime())		
		.append("Remark",getRemark())		
		.append("CreatorId",getCreatorId())		
		.append("CreateTime",getCreateTime())		
		.append("UpdatorId",getUpdatorId())		
		.append("UpdateTime",getUpdateTime())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getApiId())
		.append(getUkey())
		.append(getCount())
		.append(getTime())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EfApiAccess == false) return false;
		if(this == obj) return true;
		EfApiAccess other = (EfApiAccess)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getApiId(),other.getApiId())

		.append(getUkey(),other.getUkey())

		.append(getCount(),other.getCount())

		.append(getTime(),other.getTime())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

