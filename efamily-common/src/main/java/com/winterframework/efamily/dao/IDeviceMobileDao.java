package com.winterframework.efamily.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.DeviceMobile;

public interface IDeviceMobileDao  extends IBaseDao<DeviceMobile>{ 
	List<DeviceMobile> getByDeviceId(Long deviceId) throws BizException;
	int deleteByDeviceId(Long deviceId) throws BizException;
	List<DeviceMobile> getByOpTag(Long tag,Long beginTime,Long endTime,Long userId,Long deviceId) throws BizException;
	public Map<Date, Date> getUnhandledMaxMinTime(Long time) throws BizException;
	
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception;
}
