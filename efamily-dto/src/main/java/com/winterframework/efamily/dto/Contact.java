package com.winterframework.efamily.dto;

public class Contact {
	private String nickName;
	private String phoneNumber;
	private Integer isSos;
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
	public Integer getIsSos() {
		return isSos;
	}
	public void setIsSos(Integer isSos) {
		this.isSos = isSos;
	}
}
