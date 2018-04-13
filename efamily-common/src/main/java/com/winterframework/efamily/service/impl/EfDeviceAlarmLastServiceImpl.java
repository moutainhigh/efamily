package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfDeviceAlarmLastDao;
import com.winterframework.efamily.entity.EfDeviceAlarmLast;
import com.winterframework.efamily.service.IEfDeviceAlarmLastService;

@Service("efDeviceAlarmLastServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfDeviceAlarmLastServiceImpl  extends BaseServiceImpl<IEfDeviceAlarmLastDao,EfDeviceAlarmLast> implements IEfDeviceAlarmLastService {
	@Resource(name="efDeviceAlarmLastDaoImpl")
	private IEfDeviceAlarmLastDao dao;
	@Resource(name = "redisQueue")
	private RedisQueue redisQueue;
	
	
	@Override
	protected IEfDeviceAlarmLastDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	
	@Override
	public EfDeviceAlarmLast getByUserIdAndDeviceId(Long userId,Long deviceId) throws BizException {
		try{
			return dao.getByUserIdAndDeviceId(userId,deviceId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	

	@Override
	public EfDeviceAlarmLast getByUserIdAndType(Long userId,Integer type) throws BizException {
		try{
			return dao.getByUserIdAndType(userId,type);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	

	public int closeUserExceptionRemind(Long userId) throws BizException {
		try{
			return dao.closeUserExceptionRemind(userId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
}
