package com.winterframework.efamily.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComFamilyDao;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.service.IEjlComFamilyService;

@Service("ejlComFamilyServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComFamilyServiceImpl extends BaseServiceImpl<IEjlComFamilyDao,EjlFamily> implements IEjlComFamilyService {
	@Resource(name="ejlComFamilyDaoImpl")
	private IEjlComFamilyDao ejlComFamilyDao;

	@Override
	protected IEjlComFamilyDao getEntityDao() { 
		return ejlComFamilyDao;
	}
	
	public Long getEjlFamilyCodeId(){
		return ejlComFamilyDao.getEjlFamilyCodeId();
	}
	
}


