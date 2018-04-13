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


public class EjlFunction extends FmlBaseEntity {
	
	private static final long serialVersionUID = -3397871003172510609L;
	//alias
	public static final String TABLE_ALIAS = "EjlFunction";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_TYPE = "type";
	
	//date formats
	
	//columns START
	private String name;
	private Long type;
	//columns END

	public EjlFunction(){
	}

	public EjlFunction(
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
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
		.append("Type",getType())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getName())
		.append(getType())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlFunction == false) return false;
		if(this == obj) return true;
		EjlFunction other = (EjlFunction)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getType(),other.getType())

			.isEquals();
	}
}

