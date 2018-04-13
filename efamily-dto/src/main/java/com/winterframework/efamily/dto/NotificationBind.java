package com.winterframework.efamily.dto;

public class NotificationBind {
	private String imei;
	private boolean hasBindReq;
	private Long userId;
	private Long deviceId;
	private String nickname;
	private String phoneNumber;
	private String familyName;
	
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public boolean isHasBindReq() {
		return hasBindReq;
	}

	public void setHasBindReq(boolean hasBindReq) {
		this.hasBindReq = hasBindReq;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	
	
	
}
