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


public class EfDeviceModule extends FmlBaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "EfDeviceModule";
	public static final String ALIAS_CUSTOMER_ID = "CUSTOMERID";
	public static final String ALIAS_GLEVEL = "客户型号";
	public static final String ALIAS_MODULE_ID = "功能模块";
	public static final String ALIAS_REMARK = "注释";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Integer customerId;
	private Integer glevel;
	private Integer moduleId;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public EfDeviceModule(){
	}

	public EfDeviceModule(
		Long id
	){
		this.id = id;
	}

	public void setCustomerId(Integer value) {
		this.customerId = value;
	}
	
	public Integer getCustomerId() {
		return this.customerId;
	}
	public void setGlevel(Integer value) {
		this.glevel = value;
	}
	
	public Integer getGlevel() {
		return this.glevel;
	}
	public void setModuleId(Integer value) {
		this.moduleId = value;
	}
	
	public Integer getModuleId() {
		return this.moduleId;
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
		.append("CustomerId",getCustomerId())		
		.append("Glevel",getGlevel())		
		.append("ModuleId",getModuleId())		
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
		.append(getCustomerId())
		.append(getGlevel())
		.append(getModuleId())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EfDeviceModule == false) return false;
		if(this == obj) return true;
		EfDeviceModule other = (EfDeviceModule)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getCustomerId(),other.getCustomerId())

		.append(getGlevel(),other.getGlevel())

		.append(getModuleId(),other.getModuleId())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

