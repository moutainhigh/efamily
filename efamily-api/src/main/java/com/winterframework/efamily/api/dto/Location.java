 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.api.dto;


public class Location{
	private double longitude;	//经度
	private double latitude;	//纬度
	private Long time;	//坐标时间
	private Integer timeStay;	//停留时间
	
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
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getTimeStay() {
		return timeStay;
	}
	public void setTimeStay(Integer timeStay) {
		this.timeStay = timeStay;
	}
	
}

