package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;

public class DeviceLocationSatellite extends FmlBaseEntity{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6857295999422801114L;
	private Long deviceId;	//设备ID
	private int sateNumber;	//参与定位卫星数
	private int rate;	//星信噪比
	private Long time;	//时间
	
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public int getSateNumber() {
		return sateNumber;
	}
	public void setSateNumber(int sateNumber) {
		this.sateNumber = sateNumber;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
