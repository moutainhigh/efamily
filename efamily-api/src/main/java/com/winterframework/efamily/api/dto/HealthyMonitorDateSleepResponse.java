package com.winterframework.efamily.api.dto;


public class HealthyMonitorDateSleepResponse {

	//private List<HealthyMonitorDateSleepStruc> unitDatas;

	private Double totalTime;
	private Double deepSleepTime;
	private Double wakeSleepTime;
	private Double lightSleepTime;
	private Long startTime;
	private Long endTime;
	
	public Double getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}
	public Double getDeepSleepTime() {
		return deepSleepTime;
	}
	public void setDeepSleepTime(Double deepSleepTime) {
		this.deepSleepTime = deepSleepTime;
	}
	public Double getWakeSleepTime() {
		return wakeSleepTime;
	}
	public void setWakeSleepTime(Double wakeSleepTime) {
		this.wakeSleepTime = wakeSleepTime;
	}
	public Double getLightSleepTime() {
		return lightSleepTime;
	}
	public void setLightSleepTime(Double lightSleepTime) {
		this.lightSleepTime = lightSleepTime;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
}
