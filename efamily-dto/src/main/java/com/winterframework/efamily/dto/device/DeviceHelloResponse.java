package com.winterframework.efamily.dto.device;


public class DeviceHelloResponse {
	private Long userId;
	private Long deviceId;
	private Long fromId;
	private String nickName;
	private String phoneNumber;
	private String familyName;
	private Long chatRoomId;
	private String deviceNickName;
	private String devicePhoneNumber;
	
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
	public Long getFromId() {
		return fromId;
	}
	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public Long getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public String getDeviceNickName() {
		return deviceNickName;
	}
	public void setDeviceNickName(String deviceNickName) {
		this.deviceNickName = deviceNickName;
	}
	public String getDevicePhoneNumber() {
		return devicePhoneNumber;
	}
	public void setDevicePhoneNumber(String devicePhoneNumber) {
		this.devicePhoneNumber = devicePhoneNumber;
	}	
	
}
