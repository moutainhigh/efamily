package com.winterframework.efamily.dto;

import java.util.Date;

public class MessageNoReadInfoResponse {


	private Long messageId;
	private Long sendUserId;
	private Long receiveUserId;
	private Date sendTime;
	private Long sendStatus;
	private String content;
	private Long contentType;
	private Long contentTime;
	private Long chatType;
	private Long chatRoomId;
	private Long preMessageId=-1L;
	private Long noReadMessageCount;
	private Long status;//  状态：0 未读，1已读，2撤销，3删除
	private Long page;
	private Long pageSize;
	private Long appSendTime;
	
	
	public Long getAppSendTime() {
		return appSendTime;
	}
	public void setAppSendTime(Long appSendTime) {
		this.appSendTime = appSendTime;
	}
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Long getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Long sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getContentType() {
		return contentType;
	}
	public void setContentType(Long contentType) {
		this.contentType = contentType;
	}
	public Long getContentTime() {
		return contentTime;
	}
	public void setContentTime(Long contentTime) {
		this.contentTime = contentTime;
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
	public Long getPreMessageId() {
		return preMessageId;
	}
	public void setPreMessageId(Long preMessageId) {
		this.preMessageId = preMessageId;
	}
	public Long getNoReadMessageCount() {
		return noReadMessageCount;
	}
	public void setNoReadMessageCount(Long noReadMessageCount) {
		this.noReadMessageCount = noReadMessageCount;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "MessageNoReadInfoResponse [messageId=" + messageId
				+ ", sendUserId=" + sendUserId + ", receiveUserId="
				+ receiveUserId + ", sendTime=" + sendTime + ", sendStatus="
				+ sendStatus + ", content=" + content + ", contentType="
				+ contentType + ", contentTime=" + contentTime + ", chatType="
				+ chatType + ", chatRoomId=" + chatRoomId + ", preMessageId="
				+ preMessageId + ", noReadMessageCount=" + noReadMessageCount
				+ ", status=" + status + ", page=" + page + ", pageSize="
				+ pageSize + ", appSendTime=" + appSendTime + "]";
	}
	
}
