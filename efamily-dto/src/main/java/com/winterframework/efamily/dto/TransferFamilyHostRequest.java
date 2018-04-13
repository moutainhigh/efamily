package com.winterframework.efamily.dto;

public class TransferFamilyHostRequest {

	private Long familyId;
	private Long userId;
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
