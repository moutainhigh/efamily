package com.winterframework.efamily.dto;
public class ManageGroupSettingRequest { 
	
	private String parameterIndex;
	private String parameterContext;
	private Long chatGroupId;
	private Long oprType;
	private Long chatType;
	
	public Long getChatType() {
		return chatType;
	}
	public void setChatType(Long chatType) {
		this.chatType = chatType;
	}
	public Long getOprType() {
		return oprType;
	}
	public void setOprType(Long oprType) {
		this.oprType = oprType;
	}
	public String getParameterIndex() {
		return parameterIndex;
	}
	public void setParameterIndex(String parameterIndex) {
		this.parameterIndex = parameterIndex;
	}
	public String getParameterContext() {
		return parameterContext;
	}
	public void setParameterContext(String parameterContext) {
		this.parameterContext = parameterContext;
	}
	public Long getChatGroupId() {
		return chatGroupId;
	}
	public void setChatGroupId(Long chatGroupId) {
		this.chatGroupId = chatGroupId;
	}
	
	
	
}