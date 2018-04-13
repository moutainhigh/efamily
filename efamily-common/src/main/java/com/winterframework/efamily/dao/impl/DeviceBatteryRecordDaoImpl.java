/**   
* @Title: DeviceBatteryRecordDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午3:44:17 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IDeviceBatteryRecordDao;
import com.winterframework.efamily.entity.DeviceBatteryRecord;


/** 
* @ClassName: DeviceBatteryRecordDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-15 下午3:44:17 
*  
*/
@Repository("deviceBatteryRecordDaoImpl")
public class DeviceBatteryRecordDaoImpl<E extends DeviceBatteryRecord> extends BaseDaoImpl<DeviceBatteryRecord> implements IDeviceBatteryRecordDao{
	@Override
	public List<DeviceBatteryRecord> getByDeviceId(Long deviceId)
			throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", deviceId); 
		return sqlSessionTemplate.selectList(getQueryPath("getByDeviceId"), map);
	}
	@Override
	public int deleteByDeviceId(Long deviceId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", deviceId); 
		return sqlSessionTemplate.delete(getQueryPath("deleteByDeviceId"), map);
	}
	/**
	* Title: getLastDeviceBatteryRecordByDeviceId
	* Description:
	* @param deviceId
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IDeviceBatteryRecordDao#getLastDeviceBatteryRecordByDeviceId(java.lang.Long) 
	*/
	@Override
	public DeviceBatteryRecord getLastDeviceBatteryRecordByDeviceId(Long deviceId,Long time) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("time", time);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getLastDeviceBatteryRecordByDeviceId"), map);
	}

}
