package com.winterframework.efamily.dto;

public class MessageLastResponse {

	private String messageLastId;

	private Long noReadMessageSize ;
	
	
	public Long getNoReadMessageSize() {
		return noReadMessageSize;
	}

	public void setNoReadMessageSize(Long noReadMessageSize) {
		this.noReadMessageSize = noReadMessageSize;
	}

	public String getMessageLastId() {
		return messageLastId;
	}

	public void setMessageLastId(String messageLastId) {
		this.messageLastId = messageLastId;
	}

}
