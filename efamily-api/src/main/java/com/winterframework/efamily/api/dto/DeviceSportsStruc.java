package com.winterframework.efamily.api.dto;
public class DeviceSportsStruc {
	private Integer mode;
	private Long fromTime;
	private Long toTime;
	private Long count;
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
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
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
