 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;


 
/**
 * 血压
 * @ClassName
 * @Description
 * @author ibm
 * 2016年8月22日
 */
public class EjlHealthBloodPressure extends FmlBaseEntity {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5022830067887995955L;
	
	//columns START
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private Integer high;	//收缩压
	private Integer low;	//舒张压
	private Long fromTime;	//测试开始时间
	private Long toTime;	//测试结束时间
	private Integer rate;	//心率
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
	public Integer getHigh() {
		return high;
	}
	public void setHigh(Integer high) {
		this.high = high;
	}
	public Integer getLow() {
		return low;
	}
	public void setLow(Integer low) {
		this.low = low;
	}
	public Long getFromTime() {
		return fromTime;
	}
	public void setFromTime(Long fromTime) {
		this.fromTime = fromTime;
	}
	public Long getToTime() {
		return toTime;
	}
	public void setToTime(Long toTime) {
		this.toTime = toTime;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
}

