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
* @ClassName: LunarInitTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-15 下午1:41:20 
*  
*/
public class LocationSemiInitTask {

	private Logger log = LoggerFactory.getLogger(LocationSemiInitTask.class);

	@Resource(name = "tskLocationSemiServiceImplAdjust")
	private ITskLocationSemiServiceNew tskLocationSemiService;

	public void execute() throws Exception {
		log.debug("generate semi task begin");
		tskLocationSemiService.doProcessOrigin();
		log.debug("generate semi task end");
	}
}
