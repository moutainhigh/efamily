package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComResourceAssistDao;
import com.winterframework.efamily.entity.EfResourceAssist;
import com.winterframework.efamily.service.IEfComResourceAssistService;

@Service("efComResourceAssistServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComResourceAssistServiceImpl extends BaseServiceImpl<IEfComResourceAssistDao,EfResourceAssist> implements IEfComResourceAssistService {
 
	@Resource(name="efComResourceAssistDaoImpl")
	private IEfComResourceAssistDao efComResourceAssistDaoImpl;
	@Override
	protected IEfComResourceAssistDao getEntityDao() { 
		return efComResourceAssistDaoImpl;
	}
			
}