package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IDeviceMobileDao;
import com.winterframework.efamily.entity.DeviceMobile;
 
@Repository("deviceMobileDaoImpl")
public class DeviceMobileDaoImpl<E extends DeviceMobile> extends BaseDaoImpl<DeviceMobile> implements IDeviceMobileDao{
	@Override
	public List<DeviceMobile> getByDeviceId(Long deviceId)
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
	
	@Override
	public List<DeviceMobile> getByOpTag(Long tag, Long beginTime, Long endTime,Long userId,Long deviceId)
			throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("opTag", tag);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		return sqlSessionTemplate.selectList(getQueryPath("getByOpTag"), map);
	}
	@Override
	public Map<Date, Date> getUnhandledMaxMinTime(Long time)
			throws BizException {
		return sqlSessionTemplate.selectOne(getQueryPath("getMaxMinTimeCell"), time);
	}
	@Override
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>(); 
		map.put("timeFrom", DateUtils.convertDate2Long(timeFrom));
		map.put("timeTo", DateUtils.convertDate2Long(timeTo));
		return this.sqlSessionTemplate.selectList(getQueryPath("getDeviceIdListByFlags"), map);
	}
	
	
}
