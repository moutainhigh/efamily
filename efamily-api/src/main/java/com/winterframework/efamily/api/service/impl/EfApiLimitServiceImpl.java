package com.winterframework.efamily.api.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.dao.IEfApiLimitDao;
import com.winterframework.efamily.api.service.IEfApiLimitService;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.entity.EfApiLimit;

@Service("efApiLimitServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfApiLimitServiceImpl extends BaseServiceImpl<IEfApiLimitDao,EfApiLimit> implements IEfApiLimitService {

	@Resource(name="efApiLimitDaoImpl")
	private IEfApiLimitDao efApiLimitDaoImpl;
	
	@Override
	protected IEfApiLimitDao getEntityDao() {
		
		return efApiLimitDaoImpl;
	}


}

