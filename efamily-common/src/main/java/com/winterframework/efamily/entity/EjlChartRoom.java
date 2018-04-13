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


public class EjlChartRoom extends FmlBaseEntity {
	
	private static final long serialVersionUID = -2039521295199077110L;
	//alias
	public static final String TABLE_ALIAS = "聊天室";
	public static final String ALIAS_NAME = "名字";
	public static final String ALIAS_TYPE = "类型";
	public static final String ALIAS_STATUS = "状态";
	
	//date formats
	
	//columns START
	private String name;
	private Long type;
	private Long status;
	//columns END

	public EjlChartRoom(){
	}

	public EjlChartRoom(
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
	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
		.append("Creator",getCreator())		
		.append("GmtCreated",getGmtCreated())		
		.append("Type",getType())		
		.append("Status",getStatus())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getName())
		.append(getCreator())
		.append(getGmtCreated())
		.append(getType())
		.append(getStatus())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlChartRoom == false) return false;
		if(this == obj) return true;
		EjlChartRoom other = (EjlChartRoom)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getCreator(),other.getCreator())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getType(),other.getType())

		.append(getStatus(),other.getStatus())

			.isEquals();
	}
}

