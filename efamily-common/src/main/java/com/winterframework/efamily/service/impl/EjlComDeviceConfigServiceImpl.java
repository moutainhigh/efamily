package com.winterframework.efamily.service.impl;




import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComDeviceConfigDao;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.service.IEjlComDeviceConfigService;

@Service("ejlComDeviceConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComDeviceConfigServiceImpl extends BaseServiceImpl<IEjlComDeviceConfigDao,EjlDeviceParmConfig> implements IEjlComDeviceConfigService {
   
	@Resource(name="eJlComDeviceConfigDaoImpl")
	private IEjlComDeviceConfigDao dao;
	
	@Override
	protected IEjlComDeviceConfigDao getEntityDao() {
		return dao;
	}

	@Override
	public EjlDeviceParmConfig getByDeviceIdAndKey(Long deviceId, String key)
			throws BizException { 
		try{
			return dao.getByDeviceIdAndKey(deviceId, key);
		}catch(Exception e){
			log.error("getByDeviceIdAndKey()->",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),new String[]{String.valueOf(deviceId),key});
		}
	}
	@Override
	public int updateByDeviceAndKey(EjlDeviceParmConfig ejlDeviceParmConfig) {
		return dao.updateByDeviceAndKey( ejlDeviceParmConfig);
	}
	
}


