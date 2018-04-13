 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * 定位原始坐标
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月8日
 */
public class EfLocationOrigin extends FmlBaseEntity {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 8948569716185856705L; 
	//date formats
	
	//columns START
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private double longitude;	//经度
	private double latitude;	//纬度
	private Date time;	//坐标时间
	private Integer type;	//坐标类型
	private Integer source;	//坐标来源
	private Long sourceId;	//来源数据ID 
	private String city;	//城市
	private String address;	//地址
	private Integer status;	//状态(0 无效 1有效)
	private Integer flag;	//处理标识(0未处理 9已完成),
	private Long radius;//半径 精度

	//columns END
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	public Long getRadius() {
		return radius;
	}
	public void setRadius(Long radius) {
		this.radius = radius;
	}
	public String getLocation() {
		return latitude+","+longitude;
	}
	public void setLocation(String location) {
		this.latitude = Double.valueOf(location.split(",")[0]);
		this.longitude = Double.valueOf(location.split(",")[1]);
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(this.getDeviceId())
		.append(this.getUserId())
		.append(this.getLongitude())
		.append(this.getLatitude())
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
		if(obj instanceof EfLocationOrigin == false) return false;
		if(this == obj) return true;
		EfLocationOrigin other = (EfLocationOrigin)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getDeviceId(),other.getDeviceId())

		.append(getUserId(),other.getUserId())
		
		.append(getLongitude(),other.getLongitude())
		.append(getLatitude(),other.getLatitude())

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

