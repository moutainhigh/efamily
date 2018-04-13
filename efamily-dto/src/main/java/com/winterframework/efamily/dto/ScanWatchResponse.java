package com.winterframework.efamily.dto;
public class ScanWatchResponse { 

	private Long familyId ;

	private Long familyHostUserId;
	
	private Long deviceUserId;
	
	private Long deviceId;
	
	
	public Long getFamilyHostUserId() {
		return familyHostUserId;
	}

	public void setFamilyHostUserId(Long familyHostUserId) {
		this.familyHostUserId = familyHostUserId;
	}

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	public Long getDeviceUserId() {
		return deviceUserId;
	}

	public void setDeviceUserId(Long deviceUserId) {
		this.deviceUserId = deviceUserId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	

}