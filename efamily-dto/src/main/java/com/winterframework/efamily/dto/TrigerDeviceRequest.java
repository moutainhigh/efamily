package com.winterframework.efamily.dto;

public class TrigerDeviceRequest {
	private Integer type;//1心率 2睡眠
	private Double continueTime;
	private Long userId;
	private Long deviceId;
	private Integer status;//1开始 2结束
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getContinueTime() {
		return continueTime;
	}
	public void setContinueTime(Double continueTime) {
		this.continueTime = continueTime;
	}
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
