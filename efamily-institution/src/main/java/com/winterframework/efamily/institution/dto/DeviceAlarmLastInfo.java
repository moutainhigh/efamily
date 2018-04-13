package com.winterframework.efamily.institution.dto;

import java.util.ArrayList;
import java.util.List;

public class DeviceAlarmLastInfo {

	private Long orgUserId;
    private Long userId;
    private String userName;
    private String headImg;
 	private List<String> healthExceptionInfo = new ArrayList<String>();
 	private String location;
 	
	public Long getOrgUserId() {
		return orgUserId;
	}
	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public List<String> getHealthExceptionInfo() {
		return healthExceptionInfo;
	}
	public void setHealthExceptionInfo(List<String> healthExceptionInfo) {
		this.healthExceptionInfo = healthExceptionInfo;
	}
	@Override
	public String toString() {
		return "DeviceAlarmLastInfo [orgUserId=" + orgUserId + ", userId="
				+ userId + ", userName=" + userName + ", headImg=" + headImg
				+ ", healthExceptionInfo=" + healthExceptionInfo
				+ ", location=" + location + "]";
	}

}
