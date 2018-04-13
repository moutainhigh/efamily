package com.winterframework.efamily.dto;


public class ManageFriendShipRequest { 

	
	private   String customerId;
	private   Long oprType;
	private   Long isPhoneNumber;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Long getOprType() {
		return oprType;
	}
	public void setOprType(Long oprType) {
		this.oprType = oprType;
	}
	public Long getIsPhoneNumber() {
		return isPhoneNumber;
	}
	public void setIsPhoneNumber(Long isPhoneNumber) {
		this.isPhoneNumber = isPhoneNumber;
	}
	
	
}