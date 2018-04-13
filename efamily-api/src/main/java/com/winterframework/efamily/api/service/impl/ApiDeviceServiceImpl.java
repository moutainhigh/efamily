package com.winterframework.efamily.api.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.dao.IApiDeviceDao;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IApiDeviceService;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.service.impl.EjlComDeviceServiceImpl;
 

@Service("apiDeviceServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class ApiDeviceServiceImpl  extends EjlComDeviceServiceImpl  implements IApiDeviceService{
	@Resource(name="apiDeviceDaoImpl")
	private IApiDeviceDao apiDeviceDao;
	
	@Override
	public List<EjlDevice> getListByCustomerIdAndStatusAndTime(Context ctx,
			Long customerId, Integer status, Date time) throws BizException {
		try{
			return apiDeviceDao.getListByCustomerIdAndStatusAndTime(customerId, status, time);
		}catch(Exception e){
			log.error(StatusCode.DAO_ERROR.getValue()+"",e);
			throw new BizException(ResultCode.DB_ERROR.getCode(),e);
		}
	}
}
