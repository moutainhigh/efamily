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


public class EjlRoleFunction extends FmlBaseEntity {
	
	private static final long serialVersionUID = 2038341546254895643L;
	//alias
	public static final String TABLE_ALIAS = "EjlRoleFunction";
	public static final String ALIAS_ROLE_ID = "roleId";
	public static final String ALIAS_FUNCTION_ID = "functionId";
	
	//date formats
	
	//columns START
	private Long roleId;
	private Long functionId;
	//columns END

	public EjlRoleFunction(){
	}

	public EjlRoleFunction(
		Long id
	){
		this.id = id;
	}

	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
	public void setFunctionId(Long value) {
		this.functionId = value;
	}
	
	public Long getFunctionId() {
		return this.functionId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("RoleId",getRoleId())		
		.append("FunctionId",getFunctionId())		
		.append("GmtCreated",getGmtCreated())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getRoleId())
		.append(getFunctionId())
		.append(getGmtCreated())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlRoleFunction == false) return false;
		if(this == obj) return true;
		EjlRoleFunction other = (EjlRoleFunction)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getRoleId(),other.getRoleId())

		.append(getFunctionId(),other.getFunctionId())

		.append(getGmtCreated(),other.getGmtCreated())

			.isEquals();
	}
}

