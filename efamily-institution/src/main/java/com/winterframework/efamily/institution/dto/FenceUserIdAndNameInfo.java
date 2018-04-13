package com.winterframework.efamily.institution.dto;

public class FenceUserIdAndNameInfo {

	private Long userId;
	private String name;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "FenceUserIdAndNameInfo [userId=" + userId + ", name=" + name
				+ "]";
	}
	
}
