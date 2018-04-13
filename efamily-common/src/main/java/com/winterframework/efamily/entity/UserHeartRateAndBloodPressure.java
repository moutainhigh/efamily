package com.winterframework.efamily.entity;

public class UserHeartRateAndBloodPressure {

	private Long orgUserId;
	private Long userId;
	private String userName;
	private String bloodPressure;
	private Long heartRate;
	private String location;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getOrgUserId() {
		return orgUserId;
	}
	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public Long getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(Long heartRate) {
		this.heartRate = heartRate;
	}
	@Override
	public String toString() {
		return "UserHeartRateAndBloodPressure [orgUserId=" + orgUserId
				+ ", userId=" + userId + ", userName=" + userName
				+ ", bloodPressure=" + bloodPressure + ", heartRate="
				+ heartRate + ", location=" + location + "]";
	}


}
