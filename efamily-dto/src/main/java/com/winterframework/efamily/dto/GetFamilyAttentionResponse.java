package com.winterframework.efamily.dto;

public class GetFamilyAttentionResponse {

	private String  familyName ;
	private String  familyUserList;
	private String  attentionUserList;
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFamilyUserList() {
		return familyUserList;
	}
	public void setFamilyUserList(String familyUserList) {
		this.familyUserList = familyUserList;
	}
	public String getAttentionUserList() {
		return attentionUserList;
	}
	public void setAttentionUserList(String attentionUserList) {
		this.attentionUserList = attentionUserList;
	}
	
}
