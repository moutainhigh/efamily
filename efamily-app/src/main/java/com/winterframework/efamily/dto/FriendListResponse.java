package com.winterframework.efamily.dto;

public class FriendListResponse {

	private String nickName;
	private Long userId;
	private Integer userType;
	private String headImgUrl;
	private String phoneNumber;
	private String remarkName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRemarkName() {
		return remarkName;
	}
	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}
	@Override
	public String toString() {
		return "FriendListResponse [nickName=" + nickName + ", userId="
				+ userId + ", userType=" + userType + ", headImgUrl="
				+ headImgUrl + ", phoneNumber=" + phoneNumber + ", remarkName="
				+ remarkName + "]";
	}
	
}
