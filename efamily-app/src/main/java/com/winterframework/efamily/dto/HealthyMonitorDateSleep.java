package com.winterframework.efamily.dto;

public class HealthyMonitorDateSleep implements Comparable{

	private Long startTime;
	private Long endTime;
	private Double timeSpan;
	private Double timeSpanWake;
	private Double timeSpanDeep;
	private Long type;//1深睡 2浅睡 3清醒
	private Double totalTime;
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
	public Double getTimeSpan() {
		if(timeSpan != null){
			return Double.valueOf(new java.text.DecimalFormat("#.0").format(timeSpan));
		}
		return timeSpan;
	}
	public void setTimeSpan(Double timeSpan) {
		this.timeSpan = timeSpan;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	
	public Double getTimeSpanWake() {
		if(timeSpanWake!=null){
			return Double.valueOf(new java.text.DecimalFormat("#.0").format(timeSpanWake)); 
		}
		return timeSpanWake;
	}
	public void setTimeSpanWake(Double timeSpanWake) {
		this.timeSpanWake = timeSpanWake;
	}
	
	public Double getTimeSpanDeep() {
		if(timeSpanDeep!=null){
			return Double.valueOf(new java.text.DecimalFormat("#.0").format(timeSpanDeep)); 
		}
		return timeSpanDeep;
	}
	public void setTimeSpanDeep(Double timeSpanDeep) {
		this.timeSpanDeep = timeSpanDeep;
	}
	
	public Double getTotalTime() {
		if(totalTime!=null){
			return Double.valueOf(new java.text.DecimalFormat("#.0").format(totalTime)); 
		}
		return totalTime;
	}
	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}
	@Override
	public int compareTo(Object arg0) {
		HealthyMonitorDateSleep other = (HealthyMonitorDateSleep)arg0;
		if(this.startTime>other.getStartTime()){
			return 1;
		}else if(this.startTime<other.getStartTime()){
			return -1;
		}else {
			return 0;
		}
	}
	
	@Override
	public int hashCode() {
		return startTime.hashCode() + endTime.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		HealthyMonitorDateSleep o = (HealthyMonitorDateSleep) obj;
		return this.startTime.equals(o.getStartTime()) && this.endTime.equals(o.getEndTime());
	}
	
}
