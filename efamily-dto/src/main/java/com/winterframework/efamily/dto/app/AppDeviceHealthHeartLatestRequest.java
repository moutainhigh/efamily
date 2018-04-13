package com.winterframework.efamily.dto.app;


public class AppDeviceHealthHeartLatestRequest{
	private Long userId; 
	private Long deviceId; 
	
	
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
}
