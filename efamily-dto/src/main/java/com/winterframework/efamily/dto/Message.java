 package com.winterframework.efamily.dto;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class Message{
	 
	//alias
	public static final String TABLE_ALIAS = "聊天信息管理表";
	public static final String ALIAS_SEND_USER_ID = "消息发送方";
	public static final String ALIAS_RECEIVE_USER_ID = "消息接收方";
	public static final String ALIAS_CONTENT = "消息内容";
	public static final String ALIAS_CONTENT_TYPE = "内容类型 1 文字 2 音频 3 视频 4 图片";
	public static final String ALIAS_CHAT_TYPE = "聊天类型 ：1 对手表发消息 2 点对点 即app 对app  3 群聊（不包含手表）";
	public static final String ALIAS_CHAT_ROOM_ID = " 群聊聊天室id 当不为群聊时，此字段值为0 数据库设计约定所有字段为必填";
	
	//date formats
	
	//columns START
	private Long id;
	private Long sendUserId;
	private Long receiveUserId;
	private String content;
	private Long contentType;
	private Long chatType;
	private Long chatRoomId;
	private Long contentTime;
	private String senderName;	//发送者昵称
	
	//columns END


	public Message(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSendUserId(Long value) {
		this.sendUserId = value;
	}
	
	public Long getSendUserId() {
		return this.sendUserId;
	}
	public void setReceiveUserId(Long value) {
		this.receiveUserId = value;
	}
	
	public Long getReceiveUserId() {
		return this.receiveUserId;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContentType(Long value) {
		this.contentType = value;
	}
	
	public Long getContentType() {
		return this.contentType;
	}
	public void setChatType(Long value) {
		this.chatType = value;
	}
	
	public Long getChatType() {
		return this.chatType;
	}
	public void setChatRoomId(Long value) {
		this.chatRoomId = value;
	}
	
	public Long getChatRoomId() {
		return this.chatRoomId;
	}
	
    public Long getContentTime() {
		return contentTime;
	}

	public void setContentTime(Long contentTime) {
		this.contentTime = contentTime;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	
	
}

