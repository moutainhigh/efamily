package com.winterframework.efamily.dto;
public class SendChatMessageResponse { 


	private String sendTime ;
    private Long messageId ;
    private String appSendTime;
    

	public String getAppSendTime() {
		return appSendTime;
	}

	public void setAppSendTime(String appSendTime) {
		this.appSendTime = appSendTime;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	
}