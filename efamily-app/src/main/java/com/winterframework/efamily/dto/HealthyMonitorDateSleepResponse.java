package com.winterframework.efamily.dto;
public class HealthyMonitorDateSleepResponse {

	//private List<HealthyMonitorDateSleepStruc> unitDatas;
	private Long startTime;
	private Long endTime;
	private Double totalTime;
	private Double deepSleepTime;
	private Double wakeSleepTime;
	private Object unitDatas;
	
	public Object getUnitDatas() {
		return unitDatas;
	}
	public void setUnitDatas(Object unitDatas) {
		this.unitDatas = unitDatas;
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
	public Double getTotalTime() {
		if(totalTime != null){
			return Double.valueOf(new java.text.DecimalFormat("#.0").format(totalTime));
		}
		return totalTime;
	}
	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}
	public Double getDeepSleepTime() {
		if(deepSleepTime != null){
			return Double.valueOf(new java.text.DecimalFormat("#.0").format(deepSleepTime));
		}
		return deepSleepTime;
	}
	public void setDeepSleepTime(Double deepSleepTime) {
		this.deepSleepTime = deepSleepTime;
	}
	public Double getWakeSleepTime() {
		if(wakeSleepTime != null){
			return Double.valueOf(new java.text.DecimalFormat("#.0").format(wakeSleepTime));
		}
		return wakeSleepTime;
	}
	public void setWakeSleepTime(Double wakeSleepTime) {
		this.wakeSleepTime = wakeSleepTime;
	}
}
