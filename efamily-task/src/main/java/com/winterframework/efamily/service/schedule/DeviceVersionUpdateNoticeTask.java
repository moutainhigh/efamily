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

import com.winterframework.efamily.service.IDeviceVersionUpdateService;

 
/**
 * 设备版本更新提醒
 * @ClassName
 * @Description
 * @author ibm
 * 2016年12月28日
 */
public class DeviceVersionUpdateNoticeTask {
	private Logger log = LoggerFactory.getLogger(DeviceVersionUpdateNoticeTask.class); 
	@Resource(name = "deviceVersionUpdateServiceImpl")
	private IDeviceVersionUpdateService deviceVersionUpdateServiceImpl;

	public void execute() throws Exception {
		log.debug("deviceVersionUpdate start");
		
		deviceVersionUpdateServiceImpl.tskDeviceVersionUpdate();
		
		log.debug("deviceVersionUpdateend");
	}
		
}
