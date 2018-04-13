package com.winterframework.efamily.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.dao.IEjlHealthHeartRateDao;
import com.winterframework.efamily.api.service.IEjlHealthHeartRateService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.dao.IEjlComHealthHeartRateDao;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.service.impl.EjlComHealthHeartRateServiceImpl;

@Service("ejlHealthHeartRateServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class EjlHealthHeartRateServiceImpl extends EjlComHealthHeartRateServiceImpl implements IEjlHealthHeartRateService{

	@Resource(name = "ejlComDeviceDaoImpl")
	private IEjlComDeviceDao ejlComDeviceDaoImpl;
	
	@Resource(name = "ejlHealthHeartRateDaoImpl")
	private IEjlHealthHeartRateDao ejlHealthHeartRateDaoImpl;
	
	@Override
	public List<EjlHealthHeartRate> getHealthHeartRateByTime(String imei,
			Long fromTime, Long toTime) throws BizException {
		EjlDevice ejlDevice = null;
		List<EjlHealthHeartRate> list = new ArrayList<EjlHealthHeartRate>();
		try{
			ejlDevice = ejlComDeviceDaoImpl.getByImei(imei);
		}catch(Exception e){
			throw new BizException(100003);
		}
		if(ejlDevice == null){
			throw new BizException(100001);
		}
		else{
			try{
				/*EjlHealthHeartRate entity = new EjlHealthHeartRate();
				entity.setDeviceId(ejlDevice.getId());
				entity.setFromTime(fromTime);
				entity.setToTime(toTime);*/
				list =  ejlHealthHeartRateDaoImpl.getHealthHeartRateByTime(ejlDevice.getId(), fromTime, toTime,ejlDevice.getUserId());
			}catch(Exception e){
				log.error("",e);
				throw new BizException(100003);
			}
		}
		return list;
	}
	@Override
	public EjlHealthHeartRate getLastUserHeartRate(String imei)
			throws BizException {
		EjlDevice ejlDevice = null;
		EjlHealthHeartRate entity = null;
		try{
			ejlDevice = ejlComDeviceDaoImpl.getByImei(imei);
		}catch(Exception e){
			throw new BizException(100003);
		}
		if(ejlDevice == null){
			throw new BizException(100001);
		}
		else{
			Long time = DateUtils.convertDate2Long(DateUtils.addHours(new Date(), -12));
			try{
				entity =  ejlHealthHeartRateDaoImpl.getLastUserHeartRate(ejlDevice.getId(),ejlDevice.getUserId(),time);
			}catch(Exception e){
				log.error("",e);
				throw new BizException(100003);
			}
		}
		return entity;
	}
}
