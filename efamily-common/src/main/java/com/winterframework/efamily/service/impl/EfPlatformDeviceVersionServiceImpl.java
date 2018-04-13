package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfPlatformDeviceVersionDao;
import com.winterframework.efamily.entity.EfPlatformDeviceVersion;
import com.winterframework.efamily.service.IEfPlatformDeviceVersionService;

@Service("efPlatformDeviceVersionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfPlatformDeviceVersionServiceImpl  extends BaseServiceImpl<IEfPlatformDeviceVersionDao,EfPlatformDeviceVersion> implements IEfPlatformDeviceVersionService {
	@Resource(name="efPlatformDeviceVersionDaoImpl")
	private IEfPlatformDeviceVersionDao dao;
	@Override
	protected IEfPlatformDeviceVersionDao getEntityDao() {
		return dao;
	}
	@Override
	public EfPlatformDeviceVersion getLatestByDeviceTypeAndDeviceModelAndCustomer(
			Integer deviceType, String deviceModel,Integer customerNumber) throws BizException {
		try{
			return getEntityDao().getLatestByDeviceTypeAndDeviceModelAndCustomer(deviceType,deviceModel,customerNumber);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	@Override
	public EfPlatformDeviceVersion getByDeviceTypeAndDeviceModelAndDeviceVersion(
			Integer deviceType, String deviceModel, String deviceVersion)
			throws BizException {
		try{
			return getEntityDao().getByDeviceTypeAndDeviceModelAndDeviceVersion(deviceType,deviceModel,deviceVersion);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
}
