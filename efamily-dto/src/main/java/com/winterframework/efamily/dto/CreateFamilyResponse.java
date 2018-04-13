package com.winterframework.efamily.dto;
public class CreateFamilyResponse { 
   private Long familyId ;
   private String FamilyCode ;
   private String name ;
	
	public String getFamilyCode() {
		return FamilyCode;
	}
	
	public void setFamilyCode(String familyCode) {
		FamilyCode = familyCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

}