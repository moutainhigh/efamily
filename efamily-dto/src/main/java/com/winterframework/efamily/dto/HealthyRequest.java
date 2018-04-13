package com.winterframework.efamily.dto;

import java.util.List;

public class HealthyRequest {
	private String imei;
	private Integer sitOnff;
	private Long sitTime;
	List<String> sittingSpan;
	
	private Integer sleepOnff;
	private List<String> sleepSpan;
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Integer getSitOnff() {
		return sitOnff;
	}
	public void setSitOnff(Integer sitOnff) {
		this.sitOnff = sitOnff;
	}
	public Long getSitTime() {
		return sitTime;
	}
	public void setSitTime(Long sitTime) {
		this.sitTime = sitTime;
	}
	public List<String> getSittingSpan() {
		return sittingSpan;
	}
	public void setSittingSpan(List<String> sittingSpan) {
		this.sittingSpan = sittingSpan;
	}
	public Integer getSleepOnff() {
		return sleepOnff;
	}
	public void setSleepOnff(Integer sleepOnff) {
		this.sleepOnff = sleepOnff;
	}
	public List<String> getSleepSpan() {
		return sleepSpan;
	}
	public void setSleepSpan(List<String> sleepSpan) {
		this.sleepSpan = sleepSpan;
	}
	
	
}
