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


public class EjlChartRoomUser extends FmlBaseEntity {
	
	private static final long serialVersionUID = 4351469846559499873L;
	//alias
	public static final String TABLE_ALIAS = "聊天室人员";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_CHART_ROOM_ID = "chartRoomId";
	public static final String ALIAS_STATUS = "0，还在聊天室，1 离开";
	
	//date formats
	
	//columns START
	private Long userId;
	private Long chartRoomId;
	private Long status;
	//columns END
	
	public enum Status{ 
		PRESENT(0),
		ABSENT(1);
		private int _value;
		Status(int value){ 
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}

	public EjlChartRoomUser(){
	}

	public EjlChartRoomUser(
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
	public void setChartRoomId(Long value) {
		this.chartRoomId = value;
	}
	
	public Long getChartRoomId() {
		return this.chartRoomId;
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
		.append("UserId",getUserId())		
		.append("ChartRoomId",getChartRoomId())		
		.append("Status",getStatus())		
		.append("GmtCreated",getGmtCreated())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getChartRoomId())
		.append(getStatus())
		.append(getGmtCreated())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlChartRoomUser == false) return false;
		if(this == obj) return true;
		EjlChartRoomUser other = (EjlChartRoomUser)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getChartRoomId(),other.getChartRoomId())

		.append(getStatus(),other.getStatus())

		.append(getGmtCreated(),other.getGmtCreated())

			.isEquals();
	}
}

