package com.winterframework.efamily.dto;


public class MessageCountInfo {

	private Long messageCount;
	private Long sendUserId;
	private Long receiveUserId;
	private Long chatType;
	private Long chatRoomId;
	public Long getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(Long messageCount) {
		this.messageCount = messageCount;
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
	public Long getChatType() {
		return chatType;
	}
	public void setChatType(Long chatType) {
		this.chatType = chatType;
	}
	public Long getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

}
