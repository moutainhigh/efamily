 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;



import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlUserFriend  extends FmlBaseEntity {
	private static final long serialVersionUID = 4057779613681233021L;
	
	//alias
	public static final String TABLE_ALIAS = "用户好友表";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_FRIEND_ID = "好友id";
	public static final String ALIAS_SOURCE = "1 系统强制关联 2 主动添加 3 被动添加";
	public static final String ALIAS_STATUS = "0 好友（审核通过）  1 已申请  2 审核不通过";
	public static final String ALIAS_REMARK_NAME = "昵称";
	
	//date formats
	
	//columns START
	private Long userId;
	private Long friendId;
	private Long source;
	private Long status;
	private String remarkName;
	//columns END

	public EjlUserFriend(){
	}

	public EjlUserFriend(
		Long id
	){
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setFriendId(Long value) {
		this.friendId = value;
	}
	
	public Long getFriendId() {
		return this.friendId;
	}
	public void setSource(Long value) {
		this.source = value;
	}
	
	public Long getSource() {
		return this.source;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setRemarkName(String value) {
		this.remarkName = value;
	}
	
	public String getRemarkName() {
		return this.remarkName;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("FriendId",getFriendId())		
		.append("GmtCreated",getGmtCreated())		
		.append("Source",getSource())		
		.append("Status",getStatus())		
		.append("RemarkName",getRemarkName())		
		.append("GmtModified",getGmtModified())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getFriendId())
		.append(getGmtCreated())
		.append(getSource())
		.append(getStatus())
		.append(getRemarkName())
		.append(getGmtModified())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlUserFriend == false) return false;
		if(this == obj) return true;
		EjlUserFriend other = (EjlUserFriend)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getFriendId(),other.getFriendId())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getSource(),other.getSource())

		.append(getStatus(),other.getStatus())

		.append(getRemarkName(),other.getRemarkName())
		
		.append(getGmtModified(),other.getGmtModified())

			.isEquals();
	}
}

