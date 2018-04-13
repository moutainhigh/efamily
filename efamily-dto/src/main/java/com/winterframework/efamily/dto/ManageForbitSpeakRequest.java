package com.winterframework.efamily.dto;

public class ManageForbitSpeakRequest {

	/**
	 * userId：被禁言的用户ID
chatType：聊天室类型  
chatRoomId:聊天室ID 
oprType: 1 禁言 2 取消禁言
	 */
	
	
	private Long userId;
	private Long chatType;
	private Long chatRoomId;
	private Integer oprType;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Integer getOprType() {
		return oprType;
	}
	public void setOprType(Integer oprType) {
		this.oprType = oprType;
	}
	
}
