package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfUserHealthSettingDao;
import com.winterframework.efamily.entity.EfUserHealthSetting;
import com.winterframework.efamily.service.IEfUserHealthSettingService;

@Service("efUserHealthSettingServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfUserHealthSettingServiceImpl  extends BaseServiceImpl<IEfUserHealthSettingDao,EfUserHealthSetting> implements IEfUserHealthSettingService {
	@Resource(name="efUserHealthSettingDaoImpl")
	private IEfUserHealthSettingDao dao;
	@Override
	protected IEfUserHealthSettingDao getEntityDao() {
		return dao;
	}
	@Override
	public EfUserHealthSetting getByUserId(Long userId)
			throws BizException {
		try{
			return dao.getByUserId(userId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
}
