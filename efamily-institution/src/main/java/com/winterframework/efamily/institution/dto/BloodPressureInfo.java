package com.winterframework.efamily.institution.dto;

public class BloodPressureInfo {

	private Integer systolicPressure;
	private Integer systolicFlag;

	private Integer diastolicPressure;
	private Integer diastolicFlag;
	
	private Long fromTime;
	private Long toTime;
	public Integer getSystolicPressure() {
		return systolicPressure;
	}
	public void setSystolicPressure(Integer systolicPressure) {
		this.systolicPressure = systolicPressure;
	}
	public Integer getSystolicFlag() {
		return systolicFlag;
	}
	public void setSystolicFlag(Integer systolicFlag) {
		this.systolicFlag = systolicFlag;
	}
	public Integer getDiastolicPressure() {
		return diastolicPressure;
	}
	public void setDiastolicPressure(Integer diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}
	public Integer getDiastolicFlag() {
		return diastolicFlag;
	}
	public void setDiastolicFlag(Integer diastolicFlag) {
		this.diastolicFlag = diastolicFlag;
	}
	public Long getFromTime() {
		return fromTime;
	}
	public void setFromTime(Long fromTime) {
		this.fromTime = fromTime;
	}
	public Long getToTime() {
		return toTime;
	}
	public void setToTime(Long toTime) {
		this.toTime = toTime;
	}
	@Override
	public String toString() {
		return "BloodPressureInfo [systolicPressure=" + systolicPressure
				+ ", systolicFlag=" + systolicFlag + ", diastolicPressure="
				+ diastolicPressure + ", diastolicFlag=" + diastolicFlag
				+ ", fromTime=" + fromTime + ", toTime=" + toTime + "]";
	}
	
}
