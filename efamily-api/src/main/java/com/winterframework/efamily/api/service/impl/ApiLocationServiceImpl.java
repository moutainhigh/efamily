package com.winterframework.efamily.api.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.dao.IApiLocationDao;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IApiLocationService;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.impl.EjlComLocationServiceImpl;
 

@Service("apiLocationServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class ApiLocationServiceImpl  extends EjlComLocationServiceImpl  implements IApiLocationService{
	@Resource(name="apiLocationDaoImpl")
	private IApiLocationDao apiLocationDao;
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@Resource(name = "ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService;
	
	public List<EjlLocation> getListAfterByImeiAndTime(Context ctx,
			String imei, Date bizTime) throws BizException {
		EjlDevice device=ejlComDeviceService.getByImei(imei);
		
		if(null==device){
			throw new BizException(ResultCode.IMEI_INVALID.getCode(),new String[]{imei});
		}
		try{
			return apiLocationDao.getListAfterByDeviceIdAndTime(device.getId(), bizTime,device.getUserId());
		}catch(Exception e){
			log.error(StatusCode.DAO_ERROR.getValue()+"",e);
			throw new BizException(ResultCode.DB_ERROR.getCode(),e);
		}
	}

	@Override
	public List<EjlLocation> getListBetweenByDeviceIdAndTime(Context ctx,String imei,
			Date startTime, Date endTime) throws BizException {
		EjlDevice device=ejlComDeviceService.getByImei(imei);
		
		if(null==device){
			throw new BizException(ResultCode.IMEI_INVALID.getCode(),new String[]{imei});
		}
		try{
			return apiLocationDao.getListBetweenByDeviceIdAndTime(device.getId(), startTime, endTime,device.getUserId());
		}catch(Exception e){
			log.error(StatusCode.DAO_ERROR.getValue()+"",e);
			throw new BizException(ResultCode.DB_ERROR.getCode(),e);
		}
	}
	
	
	
}
