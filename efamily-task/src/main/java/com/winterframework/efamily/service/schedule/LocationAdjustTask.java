/**   
* @Title: LunarInitTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-15 下午1:41:20 
* @version V1.0   
*/
package com.winterframework.efamily.service.schedule;



import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.service.ITskLocationSemiServiceNew;


/**
 * 定位调整优化任务
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月26日
 */
public class LocationAdjustTask {

	private Logger log = LoggerFactory.getLogger(LocationAdjustTask.class);

	@Resource(name = "tskLocationSemiServiceImplAdjust")
	private ITskLocationSemiServiceNew tskLocationSemiService; 
	
	public void execute() throws Exception {
		log.debug("location adjust task begin");
		 
		tskLocationSemiService.doProcessNew();
		log.debug("location adjust task end");
	}
	
}
