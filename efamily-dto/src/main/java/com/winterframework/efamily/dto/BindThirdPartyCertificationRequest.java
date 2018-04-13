package com.winterframework.efamily.dto;

public class BindThirdPartyCertificationRequest {

	private Long userId;
	private String sourceType;
	private String oprType;//1  绑定  ：2 解绑
	private String sourceId;
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getOprType() {
		return oprType;
	}
	public void setOprType(String oprType) {
		this.oprType = oprType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
}
