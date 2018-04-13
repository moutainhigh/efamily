package com.winterframework.efamily.dto.device;

public class UpdateQrcodeResourceIdRequest {

	private String imei;
	private String resourceId;
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}
