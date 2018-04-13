package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfLocationWifi;

public interface IEfLocationWifiService extends IBaseService<EfLocationWifi> { 
	public List<EfLocationWifi> getByOpTag(Long opTag,Date beginDate ,Date endDate,Long userId,Long deviceId) throws BizException;
	public int update(EfLocationWifi efLocationWifi) throws BizException;
	public Map<Date, Date> getUnhandledMaxMinTime(int days) throws BizException;
	
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception;
}
