package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfDeviceSettingDao;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfDeviceSettingService;
import com.winterframework.modules.utils.SpringContextHolder;

@Service("efDeviceSettingServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfDeviceSettingServiceImpl  extends BaseServiceImpl<IEfDeviceSettingDao,EfDeviceSetting> implements IEfDeviceSettingService {
	@Resource(name="efDeviceSettingDaoImpl")
	private IEfDeviceSettingDao dao;
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	
	@Override
	protected IEfDeviceSettingDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	@Override
	public int save(Context ctx, EfDeviceSetting entity) throws BizException {
		Long userId=entity.getUserId();
		Long deviceId=entity.getDeviceId();
		String lockKey=userId+Separator.vertical+deviceId;
		
		final int lockExpireMins=300;	//5mins
		int rv=0;
		if(redisClient.lock(lockKey, lockExpireMins)){
			try{
				EfDeviceSetting deviceSetting=getByUserIdAndDeviceId(userId,deviceId);
				if(null==deviceSetting){
					deviceSetting = entity;
				}else{
					EfDeviceSetting.coverNewValue(deviceSetting, entity);
				}
				rv=super.save(ctx, deviceSetting);
			}catch(BizException e){
				log.error("device setting save failed.userId="+userId+" deviceId="+deviceId,e);
				throw e;
			}catch(Exception e2){
				log.error("device setting save failed.userId="+userId+" deviceId="+deviceId,e2);
				throw new BizException(StatusBizCode.SAVE_FAILED.getValue(),e2);
			}finally{
				redisClient.unlock(lockKey);
			}
		}
		return rv;
	}
	@Override
	public EfDeviceSetting getByUserIdAndDeviceId(Long userId,Long deviceId) throws BizException {
		try{
			return dao.getByUserIdAndDeviceId(userId,deviceId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
}
