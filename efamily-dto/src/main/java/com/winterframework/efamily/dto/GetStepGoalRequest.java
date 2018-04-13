package com.winterframework.efamily.dto;
public class GetStepGoalRequest { 

	private String type ;
	private Long queryDate ;
	private Long userId ;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Long queryDate) {
		this.queryDate = queryDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}