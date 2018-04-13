package com.winterframework.efamily.dto;
public class UpdateWatchOwnerRequest { 

    //用户ID
	private String userId ;
	//设备CODE
	private String deviceCode ;
	//被交换设备的用户：如
	private String userIdExchange ;
	//1:手表换用户  2:用户换手表  3:手表换一个新的用户  4:解除绑定
	private String oprType ;
	//手表换给一个新的用户时， 需指定该新用户的名字.
	private String newUserName ;
	//新增加手表时：需要设备电话号码
	private String phoneNumber ;
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getUserIdExchange() {
		return userIdExchange;
	}
	public void setUserIdExchange(String userIdExchange) {
		this.userIdExchange = userIdExchange;
	}
	public String getOprType() {
		return oprType;
	}
	public void setOprType(String oprType) {
		this.oprType = oprType;
	}
	public String getNewUserName() {
		return newUserName;
	}
	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}
	
	
	
}