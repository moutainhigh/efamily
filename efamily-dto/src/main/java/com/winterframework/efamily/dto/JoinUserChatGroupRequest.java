package com.winterframework.efamily.dto;
public class JoinUserChatGroupRequest { 

	private String userIds ;
	private Long chatRoomId ;
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public Long getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	
	

}