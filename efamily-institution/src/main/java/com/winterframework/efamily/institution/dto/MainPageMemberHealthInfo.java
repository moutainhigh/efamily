package com.winterframework.efamily.institution.dto;

public class MainPageMemberHealthInfo {

	private Long userId;
	private Long memberId;
	private String userName;
	private String bloodPressure;
	private String bloodPressureExceptionFlag;
	private Long heartRate;
	private String heartRateExceptionFlag;
	private Integer isOutBarrier;
	private String headImg;
	private String location;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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
	public String getBloodPressureExceptionFlag() {
		return bloodPressureExceptionFlag;
	}
	public void setBloodPressureExceptionFlag(String bloodPressureExceptionFlag) {
		this.bloodPressureExceptionFlag = bloodPressureExceptionFlag;
	}
	public Long getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(Long heartRate) {
		this.heartRate = heartRate;
	}
	public String getHeartRateExceptionFlag() {
		return heartRateExceptionFlag;
	}
	public void setHeartRateExceptionFlag(String heartRateExceptionFlag) {
		this.heartRateExceptionFlag = heartRateExceptionFlag;
	}
	public Integer getIsOutBarrier() {
		return isOutBarrier;
	}
	public void setIsOutBarrier(Integer isOutBarrier) {
		this.isOutBarrier = isOutBarrier;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "MainPageMemberHealthInfo [userId=" + userId + ", memberId="
				+ memberId + ", userName=" + userName + ", bloodPressure="
				+ bloodPressure + ", bloodPressureExceptionFlag="
				+ bloodPressureExceptionFlag + ", heartRate=" + heartRate
				+ ", heartRateExceptionFlag=" + heartRateExceptionFlag
				+ ", isOutBarrier=" + isOutBarrier + ", headImg=" + headImg
				+ ", location=" + location + "]";
	}
	
}
