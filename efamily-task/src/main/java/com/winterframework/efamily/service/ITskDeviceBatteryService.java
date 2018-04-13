/**   
* @Title: IDeviceBatteryRecordService.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午4:44:37 
* @version V1.0   
*/
package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;

 
public interface ITskDeviceBatteryService extends IDeviceBatteryRecordService {
	
	/**
	 * 处理电量频率
	 * @throws BizException
	 */
	void processFrequency() throws BizException;
	
	void doProcessFrequency(Long userId,Long deviceId) throws BizException;
}
