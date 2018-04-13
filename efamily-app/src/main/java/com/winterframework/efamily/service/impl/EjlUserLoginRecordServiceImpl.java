package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlUserLoginRecordDao;
import com.winterframework.efamily.entity.EjlUserLoginRecord;
import com.winterframework.efamily.service.IEjlUserLoginRecordService;

@Service("ejlUserLoginRecordServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlUserLoginRecordServiceImpl extends BaseServiceImpl<IEjlUserLoginRecordDao,EjlUserLoginRecord> implements IEjlUserLoginRecordService {
	@Resource(name="ejlUserLoginRecordDaoImpl")
	private IEjlUserLoginRecordDao ejlUserLoginRecordDao;
	@Override
	protected IEjlUserLoginRecordDao getEntityDao() { 
		return ejlUserLoginRecordDao;
	}
}


