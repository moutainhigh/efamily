package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IDeviceMobileDao;
import com.winterframework.efamily.entity.DeviceMobile;
import com.winterframework.efamily.service.IDeviceMobileService;

@Service("deviceMobileServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceMobileServiceImpl  extends BaseServiceImpl<IDeviceMobileDao,DeviceMobile> implements IDeviceMobileService {
	@Resource(name="deviceMobileDaoImpl")
	private IDeviceMobileDao dao;
	@Override
	protected IDeviceMobileDao getEntityDao() {
		return dao;
	}
	@Override
	public List<DeviceMobile> queryByDeviceId(Long deviceId)
			throws BizException {
		return dao.getByDeviceId(deviceId);
	}
	@Override
	public int removeByDeviceId(Long deviceId) throws BizException {
		return dao.deleteByDeviceId(deviceId);
	}
	
	@Override
	public int update(DeviceMobile deviceMobile) throws BizException {
		return dao.update(deviceMobile);
	}
	@Override
	public List<DeviceMobile> getByOpTag(Long tag, Date beginDate, Date endDate,Long userId,Long deviceId)
			throws BizException {
		Long beginTime = beginDate==null?null:DateUtils.convertDate2Long(beginDate);
		Long endTime = endDate == null?null:DateUtils.convertDate2Long(endDate);
		return dao.getByOpTag(tag, beginTime, endTime,userId,deviceId);
	}
	@Override
	public Map<Date, Date> getUnhandledMaxMinTime(int days) throws BizException {
		Long times = DateUtils.convertDate2Long(DateUtils.addDays(DateUtils.currentDate(), days));
		return dao.getUnhandledMaxMinTime(times);
	}
	@Override
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception {
		return dao.getNeedHandleDeviceList(timeFrom, timeTo);
	}	
}
