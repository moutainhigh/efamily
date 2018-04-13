package com.winterframework.efamily.service.schedule.device;

import java.util.HashMap;
import java.util.Map;
import com.winterframework.efamily.utils.Command;

public class DeviceSignalQueryTask extends BaseDeviceQueryTask{
	@Override
	public int getCommond() {
		return  Command.DEVICE_SIGNAL_QUERY.getCode();
	}

	@Override
	public Map<String, String> getParamData() {
		return new HashMap<String, String>();
	}
	
	
}
