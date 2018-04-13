package com.winterframework.efamily.dto;
public class QuitFamilyRequest { 

  private Long familyId ;

  private String userIdStr ;
  
  private Long manageType ;
  
  private Integer isAttentionOldFamilyDevice;
  
  
public Integer getIsAttentionOldFamilyDevice() {
	return isAttentionOldFamilyDevice;
}

public void setIsAttentionOldFamilyDevice(Integer isAttentionOldFamilyDevice) {
	this.isAttentionOldFamilyDevice = isAttentionOldFamilyDevice;
}

public Long getManageType() {
	return manageType;
}

public void setManageType(Long manageType) {
	this.manageType = manageType;
}

public String getUserIdStr() {
	return userIdStr;
}

public void setUserIdStr(String userIdStr) {
	this.userIdStr = userIdStr;
}

public Long getFamilyId() {
	return familyId;
}

public void setFamilyId(Long familyId) {
	this.familyId = familyId;
}
  
  
}