package com.winterframework.efamily.dto.device;


public class DeviceBatteryRecordRequest{
	private int value;	//电量
	private Long time;	//时间
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}

	
	
}
