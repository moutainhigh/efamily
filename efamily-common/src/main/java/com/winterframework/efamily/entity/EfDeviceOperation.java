 
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class EfDeviceOperation extends FmlBaseEntity {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -666603099289875607L;
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private Integer code;	//操作编码
	private Integer status;	//操作值
	private Long time; //操作时间
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
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
	

