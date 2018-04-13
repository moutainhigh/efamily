package com.winterframework.efamily.dto;

public class MessageNoReadRequest {

	private Long receiveUserId ;
	private Long sendStatus;
	public Long getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public Long getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Long sendStatus) {
		this.sendStatus = sendStatus;
	}
	
}
