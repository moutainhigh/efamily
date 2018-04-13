package com.winterframework.efamily.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.dao.IEjlHealthStepCountDao;
import com.winterframework.efamily.api.service.IEjlHealthStepCountService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthStepCount;
import com.winterframework.efamily.service.impl.EjlComHealthStepCountServiceImpl;

@Service("ejlHealthStepCountServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class EjlHealthStepCountServiceImpl extends EjlComHealthStepCountServiceImpl implements IEjlHealthStepCountService{

	@Resource(name = "ejlComDeviceDaoImpl")
	private IEjlComDeviceDao ejlComDeviceDaoImpl;
	
	@Resource(name = "ejlHealthStepCountDaoImpl")
	private IEjlHealthStepCountDao ejlHealthStepCountDaoImpl;
	
	
	
	@Override
	public List<EjlHealthStepCount> getHealthStepCountByTime(String imei,
			Long fromTime, Long endTime) throws Exception {
		EjlDevice ejlDevice = ejlComDeviceDaoImpl.getByImei(imei);
		if(ejlDevice == null){
			throw new BizException(100001);
		}
		try{
			return ejlHealthStepCountDaoImpl.getHealthStepCountByTime(ejlDevice.getId(), fromTime, endTime,ejlDevice.getUserId());
		}catch(Exception e){
			log.error("",e);
			throw new BizException(100003);
		}
	}



	@Override
	public EjlHealthStepCount getLastHealthStepCountByTime(String imei)
			throws Exception {
		EjlDevice ejlDevice = ejlComDeviceDaoImpl.getByImei(imei);
		if(ejlDevice == null){
			throw new BizException(100001);
		}
		try{
			return ejlHealthStepCountDaoImpl.getLastHealthStepCountByTime(ejlDevice.getId(),ejlDevice.getUserId());
		}catch(Exception e){
			log.error("",e);
			throw new BizException(100003);
		}
	}
	
	
}
