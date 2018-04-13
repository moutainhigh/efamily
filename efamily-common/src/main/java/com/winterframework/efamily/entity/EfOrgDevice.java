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


public class EfOrgDevice extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "EfOrgDevice";
	public static final String ALIAS_ORG_ID = "机构id";
	public static final String ALIAS_IMEI = "设备号";
	public static final String ALIAS_STATUS = "状态1禁用 0打开";
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
	private String imei;
	private Integer status;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	private String phone;
	private String devicePhone;
	private String name;
	private Long sex;
	private Long age;
	private Long height;
	private Double weight;
	private Integer guardianRelation;
	//columns END

	public EfOrgDevice(){
	}

	public EfOrgDevice(
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
	public void setImei(String value) {
		this.imei = value;
	}
	
	public String getImei() {
		return this.imei;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
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
	
	
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	public String getDevicePhone() {
		return devicePhone;
	}

	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getGuardianRelation() {
		return guardianRelation;
	}

	public void setGuardianRelation(Integer guardianRelation) {
		this.guardianRelation = guardianRelation;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("OrgId",getOrgId())		
		.append("Imei",getImei())		
		.append("Status",getStatus())		
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
		.append(getOrgId())
		.append(getImei())
		.append(getStatus())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EfOrgDevice == false) return false;
		if(this == obj) return true;
		EfOrgDevice other = (EfOrgDevice)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getOrgId(),other.getOrgId())

		.append(getImei(),other.getImei())

		.append(getStatus(),other.getStatus())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

