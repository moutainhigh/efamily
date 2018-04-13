package com.winterframework.efamily.dto.app;


public class AppDeviceHealthHeartMeasureRequest{
	private Long userId; 
	private Long deviceId; 
	private Integer type;	//操作类型1开始2结束
	
	
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
