package com.winterframework.efamily.dto;

import java.util.List;
import java.util.Map;

public class GetMonitorBloodDataResponse { 

	private List<Map<String,Object>> bloodPressure ;
	private Long diastolicRangeLt;
	private Long diastolicRangeGt;
	private Long systolicRangeLt;
	private Long systolicRangeGt;
	public List<Map<String,Object>> getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(List<Map<String,Object>> bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public Long getDiastolicRangeLt() {
		return diastolicRangeLt;
	}
	public void setDiastolicRangeLt(Long diastolicRangeLt) {
		this.diastolicRangeLt = diastolicRangeLt;
	}
	public Long getDiastolicRangeGt() {
		return diastolicRangeGt;
	}
	public void setDiastolicRangeGt(Long diastolicRangeGt) {
		this.diastolicRangeGt = diastolicRangeGt;
	}
	public Long getSystolicRangeLt() {
		return systolicRangeLt;
	}
	public void setSystolicRangeLt(Long systolicRangeLt) {
		this.systolicRangeLt = systolicRangeLt;
	}
	public Long getSystolicRangeGt() {
		return systolicRangeGt;
	}
	public void setSystolicRangeGt(Long systolicRangeGt) {
		this.systolicRangeGt = systolicRangeGt;
	}
	
}