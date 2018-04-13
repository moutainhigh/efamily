package com.winterframework.efamily.dto;

public class UpdateUserPasswordRequest {

	private String  phoneNumber;
	private String  password;
	private String  verifyCode;
	private Integer  oprType;//1 验证码方式：2 旧密码方式
	private String  oldPassword;//旧密码
	
	public Integer getOprType() {
		return oprType;
	}
	public void setOprType(Integer oprType) {
		this.oprType = oprType;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	
}
