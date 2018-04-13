package com.winterframework.efamily.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComDeviceModuleDao;
import com.winterframework.efamily.entity.EfDeviceModule;

import com.winterframework.efamily.service.IEfComDeviceModuleService;

@Service("efComDeviceModuleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComDeviceModuleServiceImpl extends BaseServiceImpl<IEfComDeviceModuleDao,EfDeviceModule> implements IEfComDeviceModuleService{

	@Resource(name="efComDeviceModuleDaoImpl")
	private IEfComDeviceModuleDao dao;
	@Override
	protected IEfComDeviceModuleDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
