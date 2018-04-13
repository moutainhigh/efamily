package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.constant.BizQueue;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfDeviceAlarmDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.service.IEfDeviceAlarmService;

@Service("efDeviceAlarmServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfDeviceAlarmServiceImpl  extends BaseServiceImpl<IEfDeviceAlarmDao,EfDeviceAlarm> implements IEfDeviceAlarmService {
	@Resource(name="efDeviceAlarmDaoImpl")
	private IEfDeviceAlarmDao dao;
	@Resource(name = "redisQueue")
	private RedisQueue redisQueue;
	
	
	@Override
	protected IEfDeviceAlarmDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	@Override
	public int save(Context ctx, EfDeviceAlarm entity) throws BizException {
		//insert alarm queue and waiting for notification
		boolean isNew=false;
		if(null==entity.getId()){	//新增
			isNew=true;
		}
		int ret=super.save(ctx, entity);
		if(isNew){
			try{
				notyAlarm(entity);
			}catch(Exception e){
				log.error("add alarm into queue error",e);
				//重试1次
				try{
					notyAlarm(entity);
				}catch(Exception e2){
					log.error("add2 alarm into queue error",e2);
				}
			}
		}
		return ret;
	}
	
	@Override
	public List<EfDeviceAlarm> getByUserIdAndDeviceId(Long userId,Long deviceId) throws BizException {
		try{
			return dao.getByUserIdAndDeviceId(userId,deviceId);
		}catch(Exception e){
			log.error("dao error.userId="+userId+" deviceId="+deviceId,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	@Override
	public List<EfDeviceAlarm> getByUserIdAndDeviceIdAndType(Long userId,
			Long deviceId, Integer type) throws BizException {
		try{
			return dao.getByUserIdAndDeviceIdAndType(userId,deviceId,type);
		}catch(Exception e){
			log.error("dao error.userId="+userId+" deviceId="+deviceId+" type="+type,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}	
	
	@Override
	public List<EfDeviceAlarm> getByUserIdAndDeviceIdAndTypeAndTime(Long userId,
			Long deviceId, Integer type,Long time) throws BizException {
		try{
			return dao.getByUserIdAndDeviceIdAndTypeAndTime(userId,deviceId,type,time);
		}catch(Exception e){
			log.error("dao error.userId="+userId+" deviceId="+deviceId+" type="+type,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}	
	
	/**
	 * 通知告警  --暂在各具体业务类中实现
	 * @throws BizException
	 */
	protected void notyAlarm(EfDeviceAlarm deviceAlarm) throws BizException{
		redisQueue.add(BizQueue.ALARM, deviceAlarm.getId()+"");
	}
}
