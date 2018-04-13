package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;

public class EjlPublicData extends FmlBaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5312995648464249740L;

	private String versionNumber;
	
	private String versionDescribe;
	
	private String updateFlag;
	
	private Integer deviceType;
	
	private String downloadUrl;
	
	private String logoUrl;
	
	private String weChat;
	
	private String weiboName;
	
	private String weiboUrl;
	
	private String phoneNumber;

	private Integer verifyCodeLength;
	
	
	public Integer getVerifyCodeLength() {
		return verifyCodeLength;
	}

	public void setVerifyCodeLength(Integer verifyCodeLength) {
		this.verifyCodeLength = verifyCodeLength;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVersionDescribe() {
		return versionDescribe;
	}

	public void setVersionDescribe(String versionDescribe) {
		this.versionDescribe = versionDescribe;
	}


	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getWeChat() {
		return weChat;
	}

	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}

	public String getWeiboName() {
		return weiboName;
	}

	public void setWeiboName(String weiboName) {
		this.weiboName = weiboName;
	}

	public String getWeiboUrl() {
		return weiboUrl;
	}

	public void setWeiboUrl(String weiboUrl) {
		this.weiboUrl = weiboUrl;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
