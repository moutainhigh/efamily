package com.winterframework.efamily.dto;
public class ManageJoinFamilyRequest { 

   private Long isPhoneNumber ;
   private String familyCode ;
   private String familyId ;
   private String applyUserid ;
   private String manageType ;
   
   
	public String getManageType() {
	return manageType;
}
public void setManageType(String manageType) {
	this.manageType = manageType;
}
	public Long getIsPhoneNumber() {
		return isPhoneNumber;
	}
	public void setIsPhoneNumber(Long isPhoneNumber) {
		this.isPhoneNumber = isPhoneNumber;
	}
	public String getFamilyCode() {
		return familyCode;
	}
	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}
	public String getApplyUserid() {
		return applyUserid;
	}
	public void setApplyUserid(String applyUserid) {
		this.applyUserid = applyUserid;
	}
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
   
	
}