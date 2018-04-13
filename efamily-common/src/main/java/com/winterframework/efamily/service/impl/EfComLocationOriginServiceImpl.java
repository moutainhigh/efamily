package com.winterframework.efamily.service.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComLocationOriginDao;
import com.winterframework.efamily.entity.EfLocationOrigin;
import com.winterframework.efamily.service.IEfComLocationOriginService;

@Service("efComLocationOriginServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComLocationOriginServiceImpl extends BaseServiceImpl<IEfComLocationOriginDao,EfLocationOrigin> implements IEfComLocationOriginService {
	@Resource(name="efComLocationOriginDaoImpl")
	private IEfComLocationOriginDao efComLocationOriginDaoImpl;
	@Override
	protected IEfComLocationOriginDao getEntityDao() { 
		return efComLocationOriginDaoImpl;
	}
	
	@Override
	public Integer updateStatus(Long id, Integer status) throws Exception {
		return efComLocationOriginDaoImpl.updateStatus(id, status);
	}
	
	
}


