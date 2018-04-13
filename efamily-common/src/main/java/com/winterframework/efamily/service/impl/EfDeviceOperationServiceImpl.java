package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfDeviceOperationDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfDeviceOperation;
import com.winterframework.efamily.service.IEfDeviceOperationService;

@Service("efDeviceOperationServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfDeviceOperationServiceImpl  extends BaseServiceImpl<IEfDeviceOperationDao,EfDeviceOperation> implements IEfDeviceOperationService {
	@Resource(name="efDeviceOperationDaoImpl")
	private IEfDeviceOperationDao dao;
	@Override
	protected IEfDeviceOperationDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	@Override
	public EfDeviceOperation getByUserIdAndDeviceId(Long userId,Long deviceId) throws BizException {
		try{
			return dao.getByUserIdAndDeviceId(userId,deviceId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfDeviceOperation getByUserIdAndDeviceIdAndCodeAndStatusAndTime(Long userId,
			Long deviceId, Integer type,Integer status, Long time) throws BizException {
		try{
			return dao.getByUserIdAndDeviceIdAndCodeAndStatusAndTime(userId,deviceId,type,status,time);
		}catch(Exception e){
			log.error("dao error.userId="+userId+" deviceId="+deviceId+" type="+type+" status="+status+" time="+time,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfDeviceOperation getLastOneByUserIdAndDeviceIdAndCodeAndTime(
			Long userId, Long deviceId, Integer code, Long time)
			throws BizException {
		try{
			return dao.getLastOneByUserIdAndDeviceIdAndCodeAndTime(userId,deviceId,code,time);
		}catch(Exception e){
			log.error("dao error.userId="+userId+" deviceId="+deviceId+" code="+code+" time="+time,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
}
