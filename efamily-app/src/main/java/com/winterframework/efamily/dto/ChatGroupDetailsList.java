package com.winterframework.efamily.dto;

import java.util.ArrayList;
import java.util.List;

public class ChatGroupDetailsList {

	private Long chatRoomId;
	private String chatRoomName;
	private Long chatRoomTop;
	private Long messageNotify;
	private Long saveAddressBook;
	
	public List<UserChartRoomMemberInfo> memberDetails = new ArrayList<UserChartRoomMemberInfo>();//*** 群组成员信息

	public Long getChatRoomId() {
		return chatRoomId;
	}
	
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public String getChatRoomName() {
		return chatRoomName;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

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

	public Long getSaveAddressBook() {
		return saveAddressBook;
	}

	public void setSaveAddressBook(Long saveAddressBook) {
		this.saveAddressBook = saveAddressBook;
	}

	public List<UserChartRoomMemberInfo> getMemberDetails() {
		return memberDetails;
	}

	public void setMemberDetails(List<UserChartRoomMemberInfo> memberDetails) {
		this.memberDetails = memberDetails;
	}

	
}
