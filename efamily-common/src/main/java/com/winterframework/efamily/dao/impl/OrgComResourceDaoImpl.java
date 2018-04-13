package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IOrgComResourceDao;
import com.winterframework.efamily.entity.OrgResource;


@Repository("orgComResourceDaoImpl")
public class OrgComResourceDaoImpl<E extends OrgResource> extends BaseDaoImpl<OrgResource> implements IOrgComResourceDao{
		
	
	
	@Override
	public OrgResource getByResourceId(String resourceId) throws Exception {
		return sqlSessionTemplate.selectOne(getQueryPath("getByResourceId"),resourceId);
	}
	
}
