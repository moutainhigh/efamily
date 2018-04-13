package com.winterframework.efamily.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfLocationGps;

public interface IEfComLocationGpsService extends IBaseService<EfLocationGps> {
	public List<EfLocationGps> getByOpTag(Long opTag,Date beginDate,Date endDate,Long userId,Long deviceId) throws BizException;
	public Map<Date, Date> getUnhandledMaxMinTime(int days)throws BizException;
	/**获取设备ID列表
	 * @param timeFrom
	 * @param timeTo
	 * @param flags	处理标识
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getNeedHandleDeviceList(Date timeFrom,Date timeTo) throws Exception;
}
