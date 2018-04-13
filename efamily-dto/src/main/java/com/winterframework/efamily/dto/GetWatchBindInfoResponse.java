package com.winterframework.efamily.dto;
public class GetWatchBindInfoResponse { 

    private String bindWatchStatus ;

    private String phoneNumber;
    
    private String familyCode;
    
    private String familyName;
    
    
    
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBindWatchStatus() {
		return bindWatchStatus;
	}

	public void setBindWatchStatus(String bindWatchStatus) {
		this.bindWatchStatus = bindWatchStatus;
	}

	@Override
	public String toString() {
		return "GetWatchBindInfoResponse [bindWatchStatus=" + bindWatchStatus
				+ ", phoneNumber=" + phoneNumber + ", familyCode=" + familyCode
				+ ", familyName=" + familyName + "]";
	}
    
    
}