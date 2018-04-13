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
import com.winterframework.orm.dal.ibatis3.BaseEntity;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlUserNotice extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 56565555L;
	//alias
	public static final String TABLE_ALIAS = "EjlUserNotice";
	public static final String ALIAS_USER_ID = "设备用户";
	public static final String ALIAS_DEVICE_USER_ID = "发送用户";
	public static final String ALIAS_RATE_STATUS = "状态0关闭 1打开";
	public static final String ALIAS_BLOOD_STATUS = "状态0关闭 1打开";
	public static final String ALIAS_DIASTOLIC_RANGE_LT = "收缩压小于值";
	public static final String ALIAS_DIASTOLIC_RANGE_GT = "收缩压大于值";
	public static final String ALIAS_SYSTOLIC_RANGE_LT = "舒张压小于值";
	public static final String ALIAS_SYSTOLIC_RANGE_GT = "收缩压大于值";
	public static final String ALIAS_RATE_RANGE_LT = "心率小于值";
	public static final String ALIAS_RATE_RANGE_GT = "心率大于值";
	public static final String ALIAS_REMARK = "注释";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private Long deviceUserId;
	private Integer rateStatus;
	private Integer bloodStatus;
	private Integer diastolicRangeLt;
	private Integer diastolicRangeGt;
	private Integer systolicRangeLt;
	private Integer systolicRangeGt;
	private Integer rateRangeLt;
	private Integer rateRangeGt;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public EjlUserNotice(){
	}

	public EjlUserNotice(
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
	public void setDeviceUserId(Long value) {
		this.deviceUserId = value;
	}
	
	public Long getDeviceUserId() {
		return this.deviceUserId;
	}
	public void setRateStatus(Integer value) {
		this.rateStatus = value;
	}
	
	public Integer getRateStatus() {
		return this.rateStatus;
	}
	public void setBloodStatus(Integer value) {
		this.bloodStatus = value;
	}
	
	public Integer getBloodStatus() {
		return this.bloodStatus;
	}
	public void setDiastolicRangeLt(Integer value) {
		this.diastolicRangeLt = value;
	}
	
	public Integer getDiastolicRangeLt() {
		return this.diastolicRangeLt;
	}
	public void setDiastolicRangeGt(Integer value) {
		this.diastolicRangeGt = value;
	}
	
	public Integer getDiastolicRangeGt() {
		return this.diastolicRangeGt;
	}
	public void setSystolicRangeLt(Integer value) {
		this.systolicRangeLt = value;
	}
	
	public Integer getSystolicRangeLt() {
		return this.systolicRangeLt;
	}
	public void setSystolicRangeGt(Integer value) {
		this.systolicRangeGt = value;
	}
	
	public Integer getSystolicRangeGt() {
		return this.systolicRangeGt;
	}
	public void setRateRangeLt(Integer value) {
		this.rateRangeLt = value;
	}
	
	public Integer getRateRangeLt() {
		return this.rateRangeLt;
	}
	public void setRateRangeGt(Integer value) {
		this.rateRangeGt = value;
	}
	
	public Integer getRateRangeGt() {
		return this.rateRangeGt;
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
		.append("UserId",getUserId())		
		.append("DeviceUserId",getDeviceUserId())		
		.append("RateStatus",getRateStatus())		
		.append("BloodStatus",getBloodStatus())		
		.append("DiastolicRangeLt",getDiastolicRangeLt())		
		.append("DiastolicRangeGt",getDiastolicRangeGt())		
		.append("SystolicRangeLt",getSystolicRangeLt())		
		.append("SystolicRangeGt",getSystolicRangeGt())		
		.append("RateRangeLt",getRateRangeLt())		
		.append("RateRangeGt",getRateRangeGt())		
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
		.append(getDeviceUserId())
		.append(getRateStatus())
		.append(getBloodStatus())
		.append(getDiastolicRangeLt())
		.append(getDiastolicRangeGt())
		.append(getSystolicRangeLt())
		.append(getSystolicRangeGt())
		.append(getRateRangeLt())
		.append(getRateRangeGt())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlUserNotice == false) return false;
		if(this == obj) return true;
		EjlUserNotice other = (EjlUserNotice)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getDeviceUserId(),other.getDeviceUserId())

		.append(getRateStatus(),other.getRateStatus())

		.append(getBloodStatus(),other.getBloodStatus())

		.append(getDiastolicRangeLt(),other.getDiastolicRangeLt())

		.append(getDiastolicRangeGt(),other.getDiastolicRangeGt())

		.append(getSystolicRangeLt(),other.getSystolicRangeLt())

		.append(getSystolicRangeGt(),other.getSystolicRangeGt())

		.append(getRateRangeLt(),other.getRateRangeLt())

		.append(getRateRangeGt(),other.getRateRangeGt())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

