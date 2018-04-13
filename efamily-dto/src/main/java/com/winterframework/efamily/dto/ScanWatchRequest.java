package com.winterframework.efamily.dto;
public class ScanWatchRequest { 

	private String watchCode ;

	private String nickName ;
	
	private String zombieUserId ;	
	
	private String phoneNumber;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWatchCode() {
		return watchCode;
	}

	public void setWatchCode(String watchCode) {
		this.watchCode = watchCode;
	}

	public String getZombieUserId() {
		return zombieUserId;
	}

	public void setZombieUserId(String zombieUserId) {
		this.zombieUserId = zombieUserId;
	}
	

}