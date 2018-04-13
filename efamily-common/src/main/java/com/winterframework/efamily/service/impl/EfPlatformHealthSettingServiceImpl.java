package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfPlatformHealthSettingDao;
import com.winterframework.efamily.entity.EfPlatformHealthSetting;
import com.winterframework.efamily.service.IEfPlatformHealthSettingService;

@Service("efPlatformHealthSettingServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfPlatformHealthSettingServiceImpl  extends BaseServiceImpl<IEfPlatformHealthSettingDao,EfPlatformHealthSetting> implements IEfPlatformHealthSettingService {
	@Resource(name="efPlatformHealthSettingDaoImpl")
	private IEfPlatformHealthSettingDao dao;
	@Override
	protected IEfPlatformHealthSettingDao getEntityDao() {
		return dao;
	}
	@Override
	public EfPlatformHealthSetting getByAgeLevel(Integer ageLevel)
			throws BizException {
		try{
			return dao.getByAgeLevel(ageLevel);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
}
