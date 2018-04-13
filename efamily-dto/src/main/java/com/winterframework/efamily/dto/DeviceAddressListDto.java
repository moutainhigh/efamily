package com.winterframework.efamily.dto;

public class DeviceAddressListDto {

	private String name;
	private String headImage;
	private String phoneNumber;
	private Long userId;
	private Long isSos;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getIsSos() {
		return isSos;
	}
	public void setIsSos(Long isSos) {
		this.isSos = isSos;
	}
	@Override
	public String toString() {
		return "DeviceAddressListDto [name=" + name + ", headImage="
				+ headImage + ", phoneNumber=" + phoneNumber + ", userId="
				+ userId + ", isSos=" + isSos + "]";
	}
	
	
}
