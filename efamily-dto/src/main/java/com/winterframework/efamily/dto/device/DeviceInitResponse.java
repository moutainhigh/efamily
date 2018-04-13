package com.winterframework.efamily.dto.device;

public class DeviceInitResponse {
	private String connect;	//连网设置	 jsonofDeviceParamConnect
	private String common;	//普通设置	jsonofDeviceParamCommon
	private String frequency; //频率设置 jsonofDeviceParamFrequency
	private String health;	//健康设置	jsonofDeviceParamHealth
	public String getConnect() {
		return connect;
	}
	public void setConnect(String connect) {
		this.connect = connect;
	}
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	
}
