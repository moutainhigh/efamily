package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.DeviceMobile;

public interface IDeviceMobileService extends IBaseService<DeviceMobile> {
	List<DeviceMobile> queryByDeviceId(Long deviceId) throws BizException;
	int removeByDeviceId(Long deviceId) throws BizException;
	List<DeviceMobile> getByOpTag(Long tag,Date beginDate,Date endDate,Long userId,Long deviceId) throws BizException;
	public int update(DeviceMobile deviceMobile) throws BizException;
	public Map<Date, Date> getUnhandledMaxMinTime(int days) throws BizException;
	
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception;
}
