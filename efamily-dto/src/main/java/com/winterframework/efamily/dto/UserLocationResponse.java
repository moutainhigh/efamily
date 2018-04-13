package com.winterframework.efamily.dto;
public class UserLocationResponse { 

	private String location ;

	private String batteryInfo ;
	
	private Long   familyId;
	private String familyName;
	
	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getBatteryInfo() {
		return batteryInfo;
	}

	public void setBatteryInfo(String batteryInfo) {
		this.batteryInfo = batteryInfo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	

}