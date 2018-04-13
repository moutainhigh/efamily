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


public class EjlUserExtendInfo extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 232323232l;
	//alias
	public static final String TABLE_ALIAS = "EjlUserExtendInfo";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_STEP_GOAL = "计步目标歩数";
	public static final String ALIAS_SIT_TIME = "久坐时间";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private Long stepGoal;
	private Float sitTime;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	private String currentCity;
	
	private String sittingSpan;	//久坐监测时间区间
	private String sleepSpan;	//睡眠监测时间区间
	//columns END

	public EjlUserExtendInfo(){
	}

	public EjlUserExtendInfo(Long id){
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setStepGoal(Long value) {
		this.stepGoal = value;
	}
	
	public Long getStepGoal() {
		return this.stepGoal;
	}
	public void setSitTime(Float value) {
		this.sitTime = value;
	}
	
	public Float getSitTime() {
		return this.sitTime;
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
	
   

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getSittingSpan() {
		return sittingSpan;
	}

	public void setSittingSpan(String sittingSpan) {
		this.sittingSpan = sittingSpan;
	}

	public String getSleepSpan() {
		return sleepSpan;
	}

	public void setSleepSpan(String sleepSpan) {
		this.sleepSpan = sleepSpan;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("StepGoal",getStepGoal())		
		.append("SitTime",getSitTime())		
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
		.append(getUserId())
		.append(getStepGoal())
		.append(getSitTime())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlUserExtendInfo == false) return false;
		if(this == obj) return true;
		EjlUserExtendInfo other = (EjlUserExtendInfo)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getStepGoal(),other.getStepGoal())

		.append(getSitTime(),other.getSitTime())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

