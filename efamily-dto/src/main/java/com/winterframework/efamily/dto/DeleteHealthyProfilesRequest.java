package com.winterframework.efamily.dto;
public class DeleteHealthyProfilesRequest { 

	private Long userId ;
	private Long deviceId ;
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

	
}