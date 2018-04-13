package com.winterframework.efamily.dto;
public class GetFamilyDetailResponse { 

	private String  familyCode ;
	private String  familyName ;
	private String  headImgUrl ;
	private Long familyHostUserId;
	
	public Long getFamilyHostUserId() {
		return familyHostUserId;
	}
	public void setFamilyHostUserId(Long familyHostUserId) {
		this.familyHostUserId = familyHostUserId;
	}
	public String getFamilyCode() {
		return familyCode;
	}
	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
}