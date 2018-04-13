package com.winterframework.efamily.service.schedule.device;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.efamily.utils.Command;

public class DeviceSleepQueryTask extends BaseDeviceQueryTask{

	@Override
	public int getCommond() {
		return Command.DEVICE_SLEEP_QUERY.getCode();
	}

	@Override
	public Map<String, String> getParamData() {
		return new HashMap<String, String>();
	}
	
	
}
