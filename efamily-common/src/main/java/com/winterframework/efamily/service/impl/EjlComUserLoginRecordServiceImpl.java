package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserLoginRecordDao;
import com.winterframework.efamily.entity.EjlUserLoginRecord;
import com.winterframework.efamily.service.IEjlComUserLoginRecordService;

@Service("ejlComUserLoginRecordServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserLoginRecordServiceImpl extends BaseServiceImpl<IEjlComUserLoginRecordDao,EjlUserLoginRecord> implements IEjlComUserLoginRecordService {
	@Resource(name="ejlComUserLoginRecordDaoImpl")
	private IEjlComUserLoginRecordDao ejlComUserLoginRecordDao;
	@Override
	protected IEjlComUserLoginRecordDao getEntityDao() { 
		return ejlComUserLoginRecordDao;
	}
}


