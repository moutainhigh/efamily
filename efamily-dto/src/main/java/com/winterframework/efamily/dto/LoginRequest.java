package com.winterframework.efamily.dto;
public class LoginRequest { 


	private String  phoneNumber;
	private String  inviteNumber;
	private String  verifyCode;
	private Long  type;
	
	private Integer  oprType;//1.密码登陆;2.验证码登陆;3.注册;4.第三方登陆5.第三方注册
	private String  password;//密码
	private String  sourceType;//1.QQ;2.weChat;3.weiBo
	private String  sourceId;//第三方认证ID

	
	public Integer getOprType() {
		return oprType;
	}
	public void setOprType(Integer oprType) {
		this.oprType = oprType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getInviteNumber() {
		return inviteNumber;
	}
	public void setInviteNumber(String inviteNumber) {
		this.inviteNumber = inviteNumber;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "LoginRequest [phoneNumber=" + phoneNumber + ", inviteNumber="
				+ inviteNumber + ", verifyCode=" + verifyCode + ", type="
				+ type + "]";
	}
	
	
	
}