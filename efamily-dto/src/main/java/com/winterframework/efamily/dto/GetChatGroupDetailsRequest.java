package com.winterframework.efamily.dto;
public class GetChatGroupDetailsRequest { 

	private Long chatGroupId ;

	private Long chatType ;

	
	public Long getChatType() {
		return chatType;
	}

	public void setChatType(Long chatType) {
		this.chatType = chatType;
	}

	public Long getChatGroupId() {
		return chatGroupId;
	}

	public void setChatGroupId(Long chatGroupId) {
		this.chatGroupId = chatGroupId;
	}

}