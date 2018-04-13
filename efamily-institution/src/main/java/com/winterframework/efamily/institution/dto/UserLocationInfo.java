package com.winterframework.efamily.institution.dto;

public class UserLocationInfo {

	private Long userId;
	private String headImg;
	private String userName;
	private String location;
    private Integer type;
    private Long time;    
    private String addressDesc;
    private Long duration;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getAddressDesc() {
		return addressDesc;
	}
	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		return "UserLocationInfo [userId=" + userId + ", headImg=" + headImg
				+ ", userName=" + userName + ", location=" + location
				+ ", type=" + type + ", time=" + time + ", addressDesc="
				+ addressDesc + ", duration=" + duration + "]";
	}
}
