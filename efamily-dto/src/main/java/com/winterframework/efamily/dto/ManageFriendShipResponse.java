package com.winterframework.efamily.dto;
public class ManageFriendShipResponse { 

	
	private String phone;
	private String customerId;
	private Long oprType;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
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
	
	
}