package com.winterframework.efamily.entity;

public class ThirdPartyLoginStruc {

	private String sourceId;
	private String sourceType;
	private Integer status;
	
	public ThirdPartyLoginStruc() {
		super();
	}
	public ThirdPartyLoginStruc(String sourceId,String sourceType, Integer status) {
		super();
		this.sourceType = sourceType;
		this.status = status;
		this.sourceId = sourceId;
	}
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
