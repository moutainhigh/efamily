package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfPlatformDeviceSettingDao;
import com.winterframework.efamily.entity.EfPlatformDeviceSetting;
import com.winterframework.efamily.service.IEfPlatformDeviceSettingService;

@Service("efPlatformDeviceSettingServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfPlatformDeviceSettingServiceImpl  extends BaseServiceImpl<IEfPlatformDeviceSettingDao,EfPlatformDeviceSetting> implements IEfPlatformDeviceSettingService {
	@Resource(name="efPlatformDeviceSettingDaoImpl")
	private IEfPlatformDeviceSettingDao dao;
	@Override
	protected IEfPlatformDeviceSettingDao getEntityDao() {
		return dao;
	}
	@Override
	public EfPlatformDeviceSetting getByDeviceType(Integer deviceType)
			throws BizException {
		try{
			return dao.getByDeviceType(deviceType);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
}
