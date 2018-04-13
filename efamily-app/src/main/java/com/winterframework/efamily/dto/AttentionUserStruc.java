package com.winterframework.efamily.dto;

public class AttentionUserStruc {
	   
	   private String nickName;
       private Long userId;
       private String headImgUrl;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	@Override
	public String toString() {
		return "AttentionUserStruc [nickName=" + nickName + ", userId="
				+ userId + ", headImgUrl=" + headImgUrl + "]";
	} 
       
       
}
