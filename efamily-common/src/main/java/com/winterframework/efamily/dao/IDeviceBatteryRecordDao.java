/**   
* @Title: IDeviceBatteryRecordDao.java 
* @Package com.winterframework.efamily.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午3:40:52 
* @version V1.0   
*/
package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.DeviceBatteryRecord;

/** 
* @ClassName: IDeviceBatteryRecordDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-15 下午3:40:52 
*  
*/
public interface IDeviceBatteryRecordDao extends IBaseDao<DeviceBatteryRecord>{
	List<DeviceBatteryRecord> getByDeviceId(Long deviceId) throws BizException;
	int deleteByDeviceId(Long deviceId) throws BizException;
	public DeviceBatteryRecord getLastDeviceBatteryRecordByDeviceId(Long deviceId,Long time) throws Exception;
}
