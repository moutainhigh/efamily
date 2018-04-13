package com.winterframework.efamily.dto;

public class FamilyAttentionUserStruc {

    private String nickName;
    private String remarkName;
    private String headImgUrl;
    private Long userId;
    private Long isForbitSpeak;
    private Long type;
    
    
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getIsForbitSpeak() {
		return isForbitSpeak;
	}
	public void setIsForbitSpeak(Long isForbitSpeak) {
		this.isForbitSpeak = isForbitSpeak;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRemarkName() {
		return remarkName;
	}
	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
}
