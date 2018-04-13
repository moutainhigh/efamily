package com.winterframework.efamily.dto;
public class HealthyProfilesRequest { 

	private Long familyId ;
	private Long deviceId ;
	private String type ;
	private Long queryDate ;
	private Long userId ;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Long queryDate) {
		this.queryDate = queryDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	 

}