package com.winterframework.efamily.dto.device;


public class DeviceContactsRequest{
	private Long userId;
	private String userName;
	private String nickName;
	private String headImage;
	private String phoneNumber;
	private Integer isSos;
	
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getIsSos() {
		return isSos;
	}
	public void setIsSos(Integer isSos) {
		this.isSos = isSos;
	}

	
}
