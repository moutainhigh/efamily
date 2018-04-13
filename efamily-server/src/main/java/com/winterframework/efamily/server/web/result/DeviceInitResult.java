package com.winterframework.efamily.server.web.result;

import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamConnect;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.dto.device.param.DeviceParamHealth;


public class DeviceInitResult extends BaseHttpResult {
	private DeviceParamConnect connect;
	private DeviceParamCommon common;
	private DeviceParamFrequency frequency;
	private DeviceParamHealth health;
	public DeviceParamConnect getConnect() {
		return connect;
	}
	public void setConnect(DeviceParamConnect connect) {
		this.connect = connect;
	}
	public DeviceParamCommon getCommon() {
		return common;
	}
	public void setCommon(DeviceParamCommon common) {
		this.common = common;
	}
	public DeviceParamFrequency getFrequency() {
		return frequency;
	}
	public void setFrequency(DeviceParamFrequency frequency) {
		this.frequency = frequency;
	}
	public DeviceParamHealth getHealth() {
		return health;
	}
	public void setHealth(DeviceParamHealth health) {
		this.health = health;
	}
	
	
	
}
