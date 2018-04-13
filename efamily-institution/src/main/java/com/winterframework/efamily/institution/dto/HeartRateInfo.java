package com.winterframework.efamily.institution.dto;

public class HeartRateInfo {
	private Long value;
	private Long fromTime;
	private Long toTime;
	private Integer flag;
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
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
		return "HeartRateInfo [value=" + value + ", fromTime=" + fromTime
				+ ", toTime=" + toTime + ", flag=" + flag + "]";
	}
	
}
