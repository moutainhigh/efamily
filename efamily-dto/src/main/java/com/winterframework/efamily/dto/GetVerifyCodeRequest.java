package com.winterframework.efamily.dto;
public class GetVerifyCodeRequest { 

	
	private String ip ;
    private String phoneNumber ;
    
    private Integer appType;

    
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	@Override
	public String toString() {
		return "GetVerifyCodeRequest [ip=" + ip + ", phoneNumber="
				+ phoneNumber + ", appType=" + appType + "]";
	}
	
	
  
}