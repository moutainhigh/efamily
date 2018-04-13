package com.winterframework.efamily.entity;

public class DeviceAlarmLastForPlatform {

    private Long userId;
    private String userName;
    private String headImg;
    private Long type;
 	private String value;
 	private Long time;
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
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "DeviceAlarmLastForPlatform [userId=" + userId + ", userName="
				+ userName + ", headImg=" + headImg + ", type=" + type
				+ ", value=" + value + ", time=" + time + "]";
	}
}
