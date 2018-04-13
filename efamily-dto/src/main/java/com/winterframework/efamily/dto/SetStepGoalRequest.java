package com.winterframework.efamily.dto;
public class SetStepGoalRequest { 

	private String type ;
	private Long  userId;
	private Long  targetStep;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTargetStep() {
		return targetStep;
	}
	public void setTargetStep(Long targetStep) {
		this.targetStep = targetStep;
	}

	
}