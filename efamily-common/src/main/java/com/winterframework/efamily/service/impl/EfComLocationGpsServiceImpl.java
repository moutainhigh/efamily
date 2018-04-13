package com.winterframework.efamily.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComLocationGpsDao;
import com.winterframework.efamily.entity.EfLocationGps;
import com.winterframework.efamily.service.IEfComLocationGpsService;


@Service("efComLocationGpsServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComLocationGpsServiceImpl  extends BaseServiceImpl<IEfComLocationGpsDao,EfLocationGps> implements IEfComLocationGpsService {
	@Resource(name="efComLocationGpsDaoImpl")
	private IEfComLocationGpsDao dao;

	@Override
	protected IEfComLocationGpsDao getEntityDao() {
		return dao;
	}


	@Override
	public List<EfLocationGps> getByOpTag(Long opTag, Date beginDate,
			Date endDate,Long userId,Long deviceId) throws BizException {
		return dao.getByOpTag(opTag, beginDate, endDate,userId,deviceId);
	}

	@Override
	public Map<Date, Date> getUnhandledMaxMinTime(int days) throws BizException {
		return dao.getUnhandledMaxMinTime(days);
	}


	@Override
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception {
		return dao.getNeedHandleDeviceList(timeFrom, timeTo);
	}	
}
