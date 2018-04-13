package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IResourceDao;
import com.winterframework.efamily.entity.FmlResource;
import com.winterframework.efamily.service.IResourceService;

@Service("resourceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl  extends BaseServiceImpl<IResourceDao,FmlResource> implements IResourceService {
	@Resource(name="resourceDaoImpl")
	private IResourceDao dao;
	@Override
	protected IResourceDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	@Override
	public FmlResource getById(String id) throws BizException {
		try {
			return dao.getById(id);
		} catch (Exception e) {
			log.error("exception when get in dao.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
}
