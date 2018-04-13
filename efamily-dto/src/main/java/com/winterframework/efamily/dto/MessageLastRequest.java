package com.winterframework.efamily.dto;

public class MessageLastRequest {


	private Long sendUserId;
	private Long receiveUserId;
	private Long chatRoomId;
	private Long chatType;
    private Long lastReadedMessageId ;
    
    
	public Long getLastReadedMessageId() {
		return lastReadedMessageId;
	}
	public void setLastReadedMessageId(Long lastReadedMessageId) {
		this.lastReadedMessageId = lastReadedMessageId;
	}
	public Long getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}
	public Long getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public Long getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public Long getChatType() {
		return chatType;
	}
	public void setChatType(Long chatType) {
		this.chatType = chatType;
	}

	
}
