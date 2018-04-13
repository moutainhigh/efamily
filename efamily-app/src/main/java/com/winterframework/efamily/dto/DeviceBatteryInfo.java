package com.winterframework.efamily.dto;

public class DeviceBatteryInfo {

	private String batteryTime ="";
	private String battery ="";
	private String deviceId ="";
	public String getBatteryTime() {
		return batteryTime;
	}
	public void setBatteryTime(String batteryTime) {
		this.batteryTime = batteryTime;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}
