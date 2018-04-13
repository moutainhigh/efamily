package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IResourceDao;
import com.winterframework.efamily.entity.FmlResource;
 
@Repository("resourceDaoImpl")
public class ResourceDaoImpl<E extends FmlResource> extends BaseDaoImpl<FmlResource> implements IResourceDao{
	@Override
	public FmlResource getById(String id) throws Exception {
		return sqlSessionTemplate.selectOne(getQueryPath("getById"),id);
	}
}
