package com.winterframework.efamily.dto;


public class SendChatMessageRequest { 

	
	private String contentType;
	private String contentTime;
	private String receiveUserId;
	private String receiveUserType;
	private String content;
	private String appSendTime;
	
	
	public String getAppSendTime() {
		return appSendTime;
	}
	public void setAppSendTime(String appSendTime) {
		this.appSendTime = appSendTime;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentTime() {
		return contentTime;
	}
	public void setContentTime(String contentTime) {
		this.contentTime = contentTime;
	}
	public String getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getReceiveUserType() {
		return receiveUserType;
	}
	public void setReceiveUserType(String receiveUserType) {
		this.receiveUserType = receiveUserType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "SendChatMessageRequest [contentType=" + contentType
				+ ", contentTime=" + contentTime + ", receiveUserId="
				+ receiveUserId + ", receiveUserType=" + receiveUserType
				+ ", content=" + content + ", appSendTime=" + appSendTime + "]";
	}

	
	
}