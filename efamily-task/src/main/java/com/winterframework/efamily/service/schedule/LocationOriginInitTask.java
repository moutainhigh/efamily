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

import com.winterframework.efamily.service.IEfLocationOriginService;


/** 
* @ClassName: LunarInitTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-15 下午1:41:20 
*  
*/
public class LocationOriginInitTask {

	private Logger log = LoggerFactory.getLogger(LocationOriginInitTask.class);

	@Resource(name = "efLocationOriginServiceImpl")
	private IEfLocationOriginService efLocationOriginServiceImpl;

	public void execute() throws Exception {
		log.debug("init OriginInit task begin");
		efLocationOriginServiceImpl.initLocationOrigin();
		log.debug("init OriginInit task end");
	}
}
