package com.winterframework.efamily.dto.device;


public class DeviceSoftwareRequest{
	private String imei;
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	
}
