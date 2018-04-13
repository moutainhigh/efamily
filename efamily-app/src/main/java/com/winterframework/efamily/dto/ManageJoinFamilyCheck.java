package com.winterframework.efamily.dto;

public class ManageJoinFamilyCheck {

	private Long optFamilyId;
	private Long oldFamilyId;
	private Long applyUserId;
	public Long getOptFamilyId() {
		return optFamilyId;
	}
	public void setOptFamilyId(Long optFamilyId) {
		this.optFamilyId = optFamilyId;
	}
	public Long getOldFamilyId() {
		return oldFamilyId;
	}
	public void setOldFamilyId(Long oldFamilyId) {
		this.oldFamilyId = oldFamilyId;
	}
	public Long getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}
	@Override
	public String toString() {
		return "ManageJoinFamilyCheck [optFamilyId=" + optFamilyId
				+ ", oldFamilyId=" + oldFamilyId + ", applyUserId="
				+ applyUserId + "]";
	}
	
}
