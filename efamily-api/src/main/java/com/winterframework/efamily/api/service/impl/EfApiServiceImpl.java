package com.winterframework.efamily.api.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.dao.IEfApiDao;
import com.winterframework.efamily.api.service.IEfApiService;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.entity.EfApi;

@Service("efApiServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfApiServiceImpl extends BaseServiceImpl<IEfApiDao,EfApi> implements IEfApiService {

	@Resource(name="efApiDaoImpl")
	private IEfApiDao efApiDaoImpl;
	
	@Override
	protected IEfApiDao getEntityDao() {
		
		return efApiDaoImpl;
	}


}

