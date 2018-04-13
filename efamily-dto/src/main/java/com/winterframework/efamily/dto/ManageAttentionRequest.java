package com.winterframework.efamily.dto;

public class ManageAttentionRequest {

	private   Long userId;//关注者
	private   String deviceCode;//关注的设备CODE
	private   Long oprType;//操作类型：1 申请关注  2 同意关注
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public Long getOprType() {
		return oprType;
	}
	public void setOprType(Long oprType) {
		this.oprType = oprType;
	}
	
}
