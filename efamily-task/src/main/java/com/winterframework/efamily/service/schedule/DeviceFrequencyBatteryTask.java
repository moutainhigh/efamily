/**   
* @Title: DeviceBatteryWarningTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-9 下午4:37:56 
* @version V1.0   
*/
package com.winterframework.efamily.service.schedule;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.service.ITskDeviceBatteryService;

 
/**
 * 设备电量频率智能控制
 * @ClassName
 * @Description
 * @author ibm
 * 2016年12月28日
 */
public class DeviceFrequencyBatteryTask {
	private Logger log = LoggerFactory.getLogger(DeviceFrequencyBatteryTask.class); 
	@Resource(name = "tskDeviceBatteryServiceImpl")
	private ITskDeviceBatteryService tskDeviceBatteryService;

	public void execute() throws Exception {
		log.debug("frequency battery start");
		
		tskDeviceBatteryService.processFrequency();
		
		log.debug("frequency battery end");
	}
		
}
