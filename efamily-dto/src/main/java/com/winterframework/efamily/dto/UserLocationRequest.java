package com.winterframework.efamily.dto;
public class UserLocationRequest { 

	private Long userId;
	private Long watchId;
	private Long recentHourType;
	private Long queryType;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getWatchId() {
		return watchId;
	}
	public void setWatchId(Long watchId) {
		this.watchId = watchId;
	}
	public Long getRecentHourType() {
		return recentHourType;
	}
	public void setRecentHourType(Long recentHourType) {
		this.recentHourType = recentHourType;
	}
	public Long getQueryType() {
		return queryType;
	}
	public void setQueryType(Long queryType) {
		this.queryType = queryType;
	}
	
	
	
}