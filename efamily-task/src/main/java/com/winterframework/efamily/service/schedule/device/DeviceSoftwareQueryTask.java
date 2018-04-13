package com.winterframework.efamily.service.schedule.device;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.utils.Command;

public class DeviceSoftwareQueryTask extends BaseDeviceQueryTask{

	@Override
	public int getCommond() {
		return Command.DEVICE_SOFTWARE_QUERY.getCode();
	}

	@Override
	public Map<String, String> getParamData() {
		return new HashMap<String, String>();
	}
	
	
}
