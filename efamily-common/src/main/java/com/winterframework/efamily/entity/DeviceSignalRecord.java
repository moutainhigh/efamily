package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;

public class DeviceSignalRecord extends FmlBaseEntity{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1444663458351231663L;
	
	private Long deviceId;	//设备ID
	private int level;	//信号格数
	private Long time;	//时间
	
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
