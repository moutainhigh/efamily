package com.winterframework.efamily.service.schedule.device;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.utils.Command;

public class DeviceBatteryQueryTask extends BaseDeviceQueryTask{

	@Override
	public int getCommond() {
		return Command.DEVICE_BATTERY_GET.getCode();
	}

	@Override
	public Map<String, String> getParamData() {
		return new HashMap<String, String>();
	}
	
	
}
