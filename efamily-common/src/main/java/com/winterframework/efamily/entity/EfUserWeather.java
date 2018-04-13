/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class EfUserWeather extends FmlBaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 23234324345l;
	// columns START
	private Long userId;
	private Long weatherId;
	private Integer status;
	private String cityName;


	// columns END

	public EfUserWeather() {
	}

	public EfUserWeather(Long id) {
		this.id = id;
	}

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getWeatherId() {
		return weatherId;
	}

	public void setWeatherId(Long weatherId) {
		this.weatherId = weatherId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder()
			.append(getUserId())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EfUserWeather == false) return false;
		if(this == obj) return true;
		EfUserWeather other = (EfUserWeather)obj;
		return new EqualsBuilder()
		.append(getUserId(),other.getUserId())
			.isEquals();
	}
	
}
