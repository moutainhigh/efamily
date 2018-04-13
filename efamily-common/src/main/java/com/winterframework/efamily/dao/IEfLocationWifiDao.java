package com.winterframework.efamily.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfLocationWifi;

public interface IEfLocationWifiDao  extends IBaseDao<EfLocationWifi>{ 
	List<EfLocationWifi> getByOpTag(Long opTag,Long beginTimes,Long endTimes,Long userId,Long deviceId) throws BizException;
	public Map<Date, Date> getUnhandledMaxMinTime(Long times) throws BizException;
	
	/**获取设备ID列表
	 * @param timeFrom
	 * @param timeTo
	 * @param flags	处理标识
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getNeedHandleDeviceList(Date timeFrom,Date timeTo) throws Exception;
}
