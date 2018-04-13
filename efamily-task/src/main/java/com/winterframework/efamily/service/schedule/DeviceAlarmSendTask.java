/**   
* @Title: GrabWeatherTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-11 上午11:09:49 
* @version V1.0   
*/
package com.winterframework.efamily.service.schedule;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.service.ITskDeviceAlarmTargetService;

 
/**
 * 设备告警任务发送
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月12日
 */
public class DeviceAlarmSendTask {
	private Logger log = LoggerFactory.getLogger(DeviceAlarmSendTask.class);

	@Resource(name = "tskDeviceAlarmTargetServiceImpl")
	private ITskDeviceAlarmTargetService tskDeviceAlarmTargetService; 
	
	public void execute() throws Exception {
		log.debug("Device alarm send task begin");
		
		tskDeviceAlarmTargetService.doProcess();
		
		log.debug("Device alarm send task end");
	}

}
