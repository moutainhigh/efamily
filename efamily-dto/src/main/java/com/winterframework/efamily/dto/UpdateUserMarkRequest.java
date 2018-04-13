package com.winterframework.efamily.dto;
public class UpdateUserMarkRequest { 


	private Long userId ;
	private String remarkName ;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRemarkName() {
		return remarkName;
	}
	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}
	
	
}