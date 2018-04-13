package com.winterframework.efamily.dto;
public class GetPublicDatasRequest { 

	byte clientType ;
	
	String appVersion ;
	
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public byte getClientType() {
		return clientType;
	}

	public void setClientType(byte clientType) {
		this.clientType = clientType;
	}
	

}