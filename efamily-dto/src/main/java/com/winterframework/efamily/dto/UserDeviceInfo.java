package com.winterframework.efamily.dto;

public class UserDeviceInfo {
	    private Long userId;
    	private String deviceType;
		private String headImgUrl;
		private String nickName;
		private String batteryTime;
		private String battery;
		private String deviceId;
		private String deviceCode;
		private String onLineStatus;
		private String phoneNumber;
		private Integer relationType;//1.家庭内部手表2.关注的手表
		private String moduleType;//customerid + glevel
		
		private Long customerId;
		private Integer glevel;
		
		private Long familyId;
		private String familyName;
		private Integer runningMode;//运行模式：0:未充电    1:正在充电
		private String qrcodeResourceId;//二维码图片的sourceId
		private Integer orgTag;
		
		public String getQrcodeResourceId() {
			return qrcodeResourceId;
		}
		public void setQrcodeResourceId(String qrcodeResourceId) {
			this.qrcodeResourceId = qrcodeResourceId;
		}
		public Integer getRunningMode() {
			return runningMode;
		}
		public void setRunningMode(Integer runningMode) {
			this.runningMode = runningMode;
		}
		public Long getFamilyId() {
			return familyId;
		}
		public void setFamilyId(Long familyId) {
			this.familyId = familyId;
		}
		public String getFamilyName() {
			return familyName;
		}
		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}
		public Integer getRelationType() {
			return relationType;
		}
		public void setRelationType(Integer relationType) {
			this.relationType = relationType;
		}
		public String getDeviceCode() {
			return deviceCode;
		}
		public void setDeviceCode(String deviceCode) {
			this.deviceCode = deviceCode;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getDeviceType() {
			return deviceType;
		}
		public void setDeviceType(String deviceType) {
			this.deviceType = deviceType;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getHeadImgUrl() {
			return headImgUrl;
		}
		public void setHeadImgUrl(String headImgUrl) {
			this.headImgUrl = headImgUrl;
		}
		public String getBattery() {
			return battery;
		}
		public void setBattery(String battery) {
			this.battery = battery;
		}
		public String getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		public String getBatteryTime() {
			return batteryTime;
		}
		public void setBatteryTime(String batteryTime) {
			this.batteryTime = batteryTime;
		}
		public String getOnLineStatus() {
			return onLineStatus;
		}
		public void setOnLineStatus(String onLineStatus) {
			this.onLineStatus = onLineStatus;
		}
		public String getModuleType() {
			return moduleType;
		}
		public void setModuleType(String moduleType) {
			this.moduleType = moduleType;
		}
		public Long getCustomerId() {
			return customerId;
		}
		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}
		public Integer getGlevel() {
			return glevel;
		}
		public void setGlevel(Integer glevel) {
			this.glevel = glevel;
		}
		public Integer getOrgTag() {
			return orgTag;
		}
		public void setOrgTag(Integer orgTag) {
			this.orgTag = orgTag;
		}
		
		
		
	}

