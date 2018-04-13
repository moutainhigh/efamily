package com.winterframework.efamily.dto.device;


public class DeviceHeartFinishRequest{
	private Integer status;
	private Long time;

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	} 
}
