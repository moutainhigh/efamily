package com.winterframework.efamily.dto.device;



public class DeviceChatMessageRequest{
	private Long senderId;	//发送用户ID
	private Long receiverId;	//接收用户ID
	private Long chatRoomId;	//聊天室ID
	private String type;	//消息类型
	private String content;	//内容字节流
	private Long contentTime;	//内容时长
	private Long time;	//时间
	private Long chatType; //聊天类型
	
	
	public Long getChatType() {
		return chatType;
	}
	public void setChatType(Long chatType) {
		this.chatType = chatType;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public Long getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	} 
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getTime() {
		return time;
	}
	public Long getContentTime() {
		return contentTime;
	}
	public void setContentTime(Long contentTime) {
		this.contentTime = contentTime;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	
	
}
