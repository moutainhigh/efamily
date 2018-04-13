 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.core.base.FmlBaseEntity;
import com.winterframework.efamily.entity.EjlMessage.ContentType;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlLocation extends FmlBaseEntity {
	
	private static final long serialVersionUID = -1465451939140009313L;
	//alias
	public static final String TABLE_ALIAS = "EjlLocation";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_LOCATION = "location";
	
	//date formats
	
	//columns START
	private Long userId;
	private Long deviceId;	//设备ID
	private String location;
	private Date time;
	private Integer source;	//坐标来源
	private Integer type;	//坐标类型
	private String des;
	private Integer status;	
	private Long sourceId;	//来源坐标ID
	private Integer timeStay;	//停留时间(秒数)
	private String address;	//地址
	private String city;
	private Long radius;//半径 精度
	private Date timeEnd;
	//columns END

	public enum Source { 
		GPS(1),	//GPS
		TOWER(2),	//基站
		WIFI(3);	//WIFI
		private int _code;
		Source(int code){
			this._code=code;
		}
		public int getCode(){
			return _code;
		}
	}
	public enum Type { 
		GPS(1),	//GPS
		AMAP(3),	//高德（gcj02ll）
		BAIDU(2);	//百度（bd09ll）
		
		private int _code;
		Type(int code){
			this._code=code;
		}
		public int getCode(){
			return _code;
		}
	}
	public EjlLocation(){
	}

	public EjlLocation(
		Long id
	){
		this.id = id;
	}

	
	public Long getRadius() {
		return radius;
	}

	public void setRadius(Long radius) {
		this.radius = radius;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public void setLocation(String value) {
		this.location = value;
	}
	
	public String getLocation() {
		return this.location;
	}
	
    public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getTimeStay() {
		return timeStay;
	}

	public void setTimeStay(Integer timeStay) {
		this.timeStay = timeStay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Override
	public String toString() {
		return "EjlLocation [userId=" + userId + ", deviceId=" + deviceId
				+ ", location=" + location + ", time=" + time + ", source="
				+ source + ", type=" + type + ", des=" + des + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(this.getDeviceId())
		.append(this.getUserId())
		.append(this.getDes())
		.append(this.getLocation())
		.append(this.getTime())
		.append(this.getSource())
		.append(this.getType())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlLocation == false) return false;
		if(this == obj) return true;
		EjlLocation other = (EjlLocation)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getDeviceId(),other.getDeviceId())

		.append(getUserId(),other.getUserId())

		.append(getDes(),other.getDes())

		.append(getLocation(),other.getLocation())

		.append(getTime(),other.getTime())

		.append(getSource(),other.getSource())

		.append(getType(),other.getType())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}

	
}

