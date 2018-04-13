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
import com.winterframework.efamily.dao.IEfLocationWifiDao;
import com.winterframework.efamily.entity.EfLocationWifi;
import com.winterframework.efamily.service.IEfLocationWifiService;

@Service("efLocationWifiServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfLocationWifiServiceImpl  extends BaseServiceImpl<IEfLocationWifiDao,EfLocationWifi> implements IEfLocationWifiService {
	@Resource(name="efLocationWifiDaoImpl")
	private IEfLocationWifiDao dao;
	@Override
	protected IEfLocationWifiDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	@Override
	public int update(EfLocationWifi efLocationWifi) throws BizException {
		return dao.update(efLocationWifi);
	}
	@Override
	public Map<Date, Date> getUnhandledMaxMinTime(int days) throws BizException{
		Long times = DateUtils.convertDate2Long(DateUtils.addDays(DateUtils.currentDate(), days));
		return dao.getUnhandledMaxMinTime(times);
	}

	@Override
	public List<EfLocationWifi> getByOpTag(Long opTag, Date beginDate,
			Date endDate,Long userId,Long deviceId) throws BizException {
		Long beginTime = beginDate==null?null:DateUtils.convertDate2Long(beginDate);
		Long endTime = endDate==null?null:DateUtils.convertDate2Long(endDate);
		return dao.getByOpTag(opTag, beginTime, endTime,userId,deviceId);
	}

	@Override
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception {
		return dao.getNeedHandleDeviceList(timeFrom, timeTo);
	}
}
