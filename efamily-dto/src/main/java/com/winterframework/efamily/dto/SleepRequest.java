package com.winterframework.efamily.dto;

import java.util.List;


public class SleepRequest{
	private Long userId;
	private Long deviceId;
	private String total;	//总睡眠时间（1433333333345-1443333333345）
	private List<String> deep;	//深度睡眠时间（1433333333345-1443333333345）
	private List<String> wake;	//清醒时间（1433333333345-1443333333345）
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<String> getDeep() {
		return deep;
	}
	public void setDeep(List<String> deep) {
		this.deep = deep;
	}
	public List<String> getWake() {
		return wake;
	}
	public void setWake(List<String> wake) {
		this.wake = wake;
	}
	
	

	
}
