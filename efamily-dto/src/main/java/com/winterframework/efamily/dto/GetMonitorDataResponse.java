package com.winterframework.efamily.dto;
public class GetMonitorDataResponse { 

	private String healthyMonitorData ;
	private Long totalTime;
	
	private Long topValue;
	private Long bottomValue;
	private Long middleValue;
	private Long rateRangeLt;
	private Long rateRangeGt;
	private Long operUserId;
	private String operUserName;

	public String getHealthyMonitorData() {
		return healthyMonitorData;
	}

	public void setHealthyMonitorData(String healthyMonitorData) {
		this.healthyMonitorData = healthyMonitorData;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public Long getTopValue() {
		return topValue;
	}

	public void setTopValue(Long topValue) {
		this.topValue = topValue;
	}

	public Long getBottomValue() {
		return bottomValue;
	}

	public void setBottomValue(Long bottomValue) {
		this.bottomValue = bottomValue;
	}

	public Long getMiddleValue() {
		return middleValue;
	}

	public void setMiddleValue(Long middleValue) {
		this.middleValue = middleValue;
	}

	public Long getRateRangeLt() {
		return rateRangeLt;
	}

	public void setRateRangeLt(Long rateRangeLt) {
		this.rateRangeLt = rateRangeLt;
	}

	public Long getRateRangeGt() {
		return rateRangeGt;
	}

	public void setRateRangeGt(Long rateRangeGt) {
		this.rateRangeGt = rateRangeGt;
	}
	
	public Long getOperUserId() {
		return operUserId;
	}

	public void setOperUserId(Long operUserId) {
		this.operUserId = operUserId;
	}

	public String getOperUserName() {
		return operUserName;
	}

	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}

	
	
}