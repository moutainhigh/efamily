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

import com.winterframework.efamily.service.ITskDeviceAlarmService;

 
/**
 * 设备告警任务
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月12日
 */
public class DeviceAlarmTask {
	private Logger log = LoggerFactory.getLogger(DeviceAlarmTask.class);

	@Resource(name = "tskDeviceAlarmServiceImpl")
	private ITskDeviceAlarmService tskDeviceAlarmService; 
	
	public void execute() throws Exception {
		log.debug("Device alarm task begin");
		
		tskDeviceAlarmService.doProcess();
		
		log.debug("Device alarm task end");
	}

}
