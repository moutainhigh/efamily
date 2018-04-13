package com.winterframework.efamily.dto;
public class SendInviteMessageRequest { 

private Long userId ;
private Long familyId ;
private String invitePhoneNumber ;
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public Long getFamilyId() {
	return familyId;
}
public void setFamilyId(Long familyId) {
	this.familyId = familyId;
}
public String getInvitePhoneNumber() {
	return invitePhoneNumber;
}
public void setInvitePhoneNumber(String invitePhoneNumber) {
	this.invitePhoneNumber = invitePhoneNumber;
}



}