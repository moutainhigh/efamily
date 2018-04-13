package com.winterframework.efamily.dto;

import java.util.ArrayList;
import java.util.List;

public class ChatGroupListResponse {

	public Long chatGroupId ;//*** 群组ID
	public String chatRoomName ;//*** 群组名称
	public Long chatRoomTop  ;// 聊天组置顶
	public Long messageNotify ;// 消息是否通知
	public List<String> chatGroupUserIcons = new ArrayList<String>();//*** 群组成员头像
	
	public Long getChatRoomTop() {
		return chatRoomTop;
	}
	public void setChatRoomTop(Long chatRoomTop) {
		this.chatRoomTop = chatRoomTop;
	}
	public Long getMessageNotify() {
		return messageNotify;
	}
	public void setMessageNotify(Long messageNotify) {
		this.messageNotify = messageNotify;
	}
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
	public List<String> getChatGroupUserIcons() {
		return chatGroupUserIcons;
	}
	public void setChatGroupUserIcons(List<String> chatGroupUserIcons) {
		this.chatGroupUserIcons = chatGroupUserIcons;
	}
	
}
