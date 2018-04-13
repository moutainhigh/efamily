package com.winterframework.efamily.dto;

public class ChatRoomUserNotify {

	private String userName;
	private String icon;
	private String userId;
	private String sex;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ChatRoomUserNotify [userName=" + userName + ", icon=" + icon
				+ ", userId=" + userId + "]";
	}
	
	
}
