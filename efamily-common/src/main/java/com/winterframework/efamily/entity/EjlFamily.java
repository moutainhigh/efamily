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


public class EjlFamily extends FmlBaseEntity {
	
	private static final long serialVersionUID = 5151011964613315822L;
	//alias
	public static final String TABLE_ALIAS = "EjlFamily";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_CODE = "code";
	
	//date formats
	
	//columns START
	private String name;
	private String code;
	private String headImg;
	//columns END

	public EjlFamily(){
	}

	public EjlFamily(
		Long id
	){
		this.id = id;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
    public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
		.append("Creator",getCreator())		
		.append("GmtCreated",getGmtCreated())		
		.append("Code",getCode())
		.append("headImg",getHeadImg())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getName())
		.append(getCreator())
		.append(getGmtCreated())
		.append(getCode())
		.append(getHeadImg())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlFamily == false) return false;
		if(this == obj) return true;
		EjlFamily other = (EjlFamily)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getCreator(),other.getCreator())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getCode(),other.getCode())
		.append(getHeadImg(),other.getHeadImg())
			.isEquals();
	}
}

