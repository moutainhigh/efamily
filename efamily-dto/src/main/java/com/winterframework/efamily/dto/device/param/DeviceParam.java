package com.winterframework.efamily.dto.device.param;

import com.winterframework.efamily.dto.WatchDeviceManageRequest;

public class DeviceParam {
	private WatchDeviceManageRequest  param;
	private String imei;
	public WatchDeviceManageRequest getParam() {
		return param;
	}
	public void setParam(WatchDeviceManageRequest param) {
		this.param = param;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
}
