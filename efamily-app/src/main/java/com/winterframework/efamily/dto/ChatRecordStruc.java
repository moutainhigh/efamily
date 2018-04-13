package com.winterframework.efamily.dto;

public class ChatRecordStruc {
	private Long userId;
	private Long sendTime;
	private Long contentTime;
	private String content;
	private Integer contentType;
	private Integer readStatus;
	private Long messageId;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSendTime() {
		return sendTime;
	}
	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	public Long getContentTime() {
		return contentTime;
	}
	public void setContentTime(Long contentTime) {
		this.contentTime = contentTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public Integer getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
}
