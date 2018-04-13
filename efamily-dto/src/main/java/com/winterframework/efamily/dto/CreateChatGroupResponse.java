package com.winterframework.efamily.dto;
public class CreateChatGroupResponse { 
	private Long chatGroupId;
	private String chatRoomName;
	public Long getChatGroupId() {
		return chatGroupId;
	}
	public void setChatGroupId(Long chatGroupId) {
		this.chatGroupId = chatGroupId;
	}
	public String getChatRoomName() {
		return chatRoomName;
	}
	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
	
}