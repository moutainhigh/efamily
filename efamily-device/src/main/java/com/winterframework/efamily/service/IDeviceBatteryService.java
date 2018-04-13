/**   
* @Title: IDeviceBatteryRecordService.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午4:44:37 
* @version V1.0   
*/
package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.DeviceBatteryRecord;

/** 
* @ClassName: IDeviceBatteryRecordService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-15 下午4:44:37 
*  
*/
public interface IDeviceBatteryService extends IDeviceBatteryRecordService {
	void save(Context ctx,Long userId,Long deviceId,List<DeviceBatteryRecord> batteryList) throws BizException;
}
