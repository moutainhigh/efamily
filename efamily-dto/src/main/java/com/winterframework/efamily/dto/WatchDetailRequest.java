package com.winterframework.efamily.dto;
public class WatchDetailRequest { 


	private String userId;
	private String watchId ;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWatchId() {
		return watchId;
	}
	public void setWatchId(String watchId) {
		this.watchId = watchId;
	}
	@Override
	public String toString() {
		return "WatchDetailRequest [userId=" + userId + ", watchId=" + watchId
				+ "]";
	}
	
	
}