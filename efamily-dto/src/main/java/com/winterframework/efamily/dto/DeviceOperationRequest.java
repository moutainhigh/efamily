package com.winterframework.efamily.dto;

public class DeviceOperationRequest {
	private Integer code;	//操作编码
	private Long time;	//操作时间
	private Integer status;//操作状态
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}

	
}
