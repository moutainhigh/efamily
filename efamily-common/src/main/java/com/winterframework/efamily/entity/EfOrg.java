 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EfOrg extends FmlBaseEntity	 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "EfOrg";
	public static final String ALIAS_NAME = "机构名称";
	public static final String ALIAS_NUMBER = "编号";
	public static final String ALIAS_CUSTOMER_ID = "客户编号";
	public static final String ALIAS_SCALE = "规模";
	public static final String ALIAS_PROVINCE = "省份";
	public static final String ALIAS_CITY = "城市";
	public static final String ALIAS_COUNTY = "县区";
	public static final String ALIAS_PHONE = "联系电话";
	public static final String ALIAS_IKEY = "机构key";
	public static final String ALIAS_UKEY = "客户key";
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
	private String name;
	private String number;
	private Long customerId;
	private String scale;
	private String province;
	private String city;
	private String county;
	private String phone;
	private String ikey;
	private String ukey;
	private Integer status;
	//columns END

	public EfOrg(){
	}

	public EfOrg(
		Long id
	){
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setNumber(String value) {
		this.number = value;
	}
	
	public String getNumber() {
		return this.number;
	}
	public void setScale(String value) {
		this.scale = value;
	}
	
	public String getScale() {
		return this.scale;
	}
	public void setCity(String value) {
		this.city = value;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setIkey(String value) {
		this.ikey = value;
	}
	
	public String getIkey() {
		return this.ikey;
	}
	public void setUkey(String value) {
		this.ukey = value;
	}
	
	public String getUkey() {
		return this.ukey;
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
	
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
		.append("Number",getNumber())	
		.append("CustomerId",getCustomerId())
		.append("Scale",getScale())		
		.append("Province",getProvince())		
		.append("City",getCity())		
		.append("County",getCity())		
		.append("Phone",getPhone())		
		.append("Ikey",getIkey())		
		.append("Ukey",getUkey())		
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
		.append(getName())
		.append(getNumber())
		.append(getCustomerId())
		.append(getScale())
		.append(getProvince())
		.append(getCity())
		.append(getCounty())
		.append(getPhone())
		.append(getIkey())
		.append(getUkey())
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
		if(obj instanceof EfOrg == false) return false;
		if(this == obj) return true;
		EfOrg other = (EfOrg)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getNumber(),other.getNumber())
		.append(getNumber(),other.getCustomerId())

		.append(getScale(),other.getScale())

		.append(getCity(),other.getProvince())
		.append(getCity(),other.getCity())
		.append(getCity(),other.getCounty())

		.append(getPhone(),other.getPhone())

		.append(getIkey(),other.getIkey())

		.append(getUkey(),other.getUkey())

		.append(getStatus(),other.getStatus())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

