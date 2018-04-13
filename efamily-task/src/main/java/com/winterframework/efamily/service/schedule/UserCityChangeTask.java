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

import com.winterframework.efamily.service.IWeatherService;

 
/**
 * 用户城市变更
 * @ClassName
 * @Description
 * @author ibm
 * 2016年4月1日
 */
public class UserCityChangeTask {
	private Logger log = LoggerFactory.getLogger(UserCityChangeTask.class);

	@Resource(name = "weatherServiceImpl")
	private IWeatherService weatherService; 
	public void execute() throws Exception {
		log.debug("UserCityChangeTask begin");
		weatherService.cityChange();
		log.debug("UserCityChangeTask end");
	}

}
