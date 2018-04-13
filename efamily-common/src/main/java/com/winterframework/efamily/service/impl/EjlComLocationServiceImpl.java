package com.winterframework.efamily.service.impl;


import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.service.IEjlComLocationService;

@Service("ejlComLocationServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComLocationServiceImpl extends BaseServiceImpl<IEjlComLocationDao,EjlLocation> implements IEjlComLocationService {
	@Resource(name="ejlComLocationDaoImpl")
	private IEjlComLocationDao ejlComLocationDaoImpl;
	@Override
	protected IEjlComLocationDao getEntityDao() { 
		return ejlComLocationDaoImpl;
	}
	@Override
	public EjlLocation getUserLatestLocation(Long userId, Long watchId,Integer status,Date time) {
		return ejlComLocationDaoImpl.getUserLatestLocation(userId, watchId,status,time);
	}
	
	public EjlLocation getDeviceLocationLatest(String imei){
		//屏蔽掉无用的方法，没有按时间查询
		//return ejlComLocationDaoImpl.getDeviceLocationLatest(imei);
		return null;
	}
	@Override
	public EjlLocation getBySourceId(Long sourceId) throws BizException {
		return ejlComLocationDaoImpl.getBySourceId(sourceId);
	}
}


