package com.winterframework.efamily.service.schedule;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.service.IDeviceHealthyDataPushService;


public class DeviceHealthyDataPushTask {
	private Logger log = LoggerFactory.getLogger(DeviceHealthyDataPushTask.class);

	@Resource(name = "deviceHealthyDataPushServiceImpl")
	private IDeviceHealthyDataPushService deviceHealthyDataPushServiceImpl; 
	
	public void execute() throws Exception {
		log.debug("Device healthy data send task begin");
		
		deviceHealthyDataPushServiceImpl.doProcess();
		
		log.debug("Device healthy data send task end");
	}

}
