package com.winterframework.efamily.dto;

public class UnbindDeviceInfo {

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
	@Override
	public String toString() {
		return "UnbindDeviceInfo [userId=" + userId + ", deviceId=" + deviceId
				+ "]";
	}
	
}
