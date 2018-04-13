package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComResourceDao;
import com.winterframework.efamily.entity.OrgResource;
import com.winterframework.efamily.service.IOrgComResourceService;


@Service("orgComResourceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComResourceServiceImpl  extends BaseServiceImpl<IOrgComResourceDao,OrgResource> implements IOrgComResourceService {
	@Resource(name="orgComResourceDaoImpl")
	private IOrgComResourceDao dao;
	@Override
	protected IOrgComResourceDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public OrgResource getByResourceId(String resourceId) throws Exception{
		return getEntityDao().getByResourceId(resourceId);
	}
}
