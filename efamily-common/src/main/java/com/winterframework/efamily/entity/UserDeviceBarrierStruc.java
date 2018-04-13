package com.winterframework.efamily.entity;

public class UserDeviceBarrierStruc {

	private Long userBarrierId;
	private Long userId;
	private Long deviceId;
	private String location;
	private Long radius;
	private String barrierRemark;
	private Integer orgTag;
	
	
	public String getBarrierRemark() {
		return barrierRemark;
	}
	public void setBarrierRemark(String barrierRemark) {
		this.barrierRemark = barrierRemark;
	}
	public Long getUserBarrierId() {
		return userBarrierId;
	}
	public void setUserBarrierId(Long userBarrierId) {
		this.userBarrierId = userBarrierId;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getRadius() {
		return radius;
	}
	public void setRadius(Long radius) {
		this.radius = radius;
	}
	
	public Integer getOrgTag() {
		return orgTag;
	}
	public void setOrgTag(Integer orgTag) {
		this.orgTag = orgTag;
	}
	@Override
	public String toString() {
		return "UserDeviceBarrierStruc [userBarrierId=" + userBarrierId
				+ ", userId=" + userId + ", deviceId=" + deviceId
				+ ", location=" + location + ", radius=" + radius
				+ ", barrierRemark=" + barrierRemark + "]";
	}
	
}
