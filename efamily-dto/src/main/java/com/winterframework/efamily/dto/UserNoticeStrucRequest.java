package com.winterframework.efamily.dto;

public class UserNoticeStrucRequest {
	private Long id;
	private Long deviceUserId;
	private Integer rateStatus;
	private Integer bloodStatus;
	private Integer diastolicRangeLt;
	private Integer diastolicRangeGt;
	private Integer systolicRangeLt;
	private Integer systolicRangeGt;
	private Integer rateRangeLt;
	private Integer rateRangeGt;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDeviceUserId() {
		return deviceUserId;
	}
	public void setDeviceUserId(Long deviceUserId) {
		this.deviceUserId = deviceUserId;
	}
	public Integer getRateStatus() {
		return rateStatus;
	}
	public void setRateStatus(Integer rateStatus) {
		this.rateStatus = rateStatus;
	}
	public Integer getBloodStatus() {
		return bloodStatus;
	}
	public void setBloodStatus(Integer bloodStatus) {
		this.bloodStatus = bloodStatus;
	}
	public Integer getDiastolicRangeLt() {
		return diastolicRangeLt;
	}
	public void setDiastolicRangeLt(Integer diastolicRangeLt) {
		this.diastolicRangeLt = diastolicRangeLt;
	}
	public Integer getDiastolicRangeGt() {
		return diastolicRangeGt;
	}
	public void setDiastolicRangeGt(Integer diastolicRangeGt) {
		this.diastolicRangeGt = diastolicRangeGt;
	}
	public Integer getSystolicRangeLt() {
		return systolicRangeLt;
	}
	public void setSystolicRangeLt(Integer systolicRangeLt) {
		this.systolicRangeLt = systolicRangeLt;
	}
	public Integer getSystolicRangeGt() {
		return systolicRangeGt;
	}
	public void setSystolicRangeGt(Integer systolicRangeGt) {
		this.systolicRangeGt = systolicRangeGt;
	}
	public Integer getRateRangeLt() {
		return rateRangeLt;
	}
	public void setRateRangeLt(Integer rateRangeLt) {
		this.rateRangeLt = rateRangeLt;
	}
	public Integer getRateRangeGt() {
		return rateRangeGt;
	}
	public void setRateRangeGt(Integer rateRangeGt) {
		this.rateRangeGt = rateRangeGt;
	}
	
	
	
}
