package com.winterframework.efamily.dto;

public class GetThirdPartyLinkPhoneNumberRequest {

	private String sourceId;

	private Integer sourceType;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
}
