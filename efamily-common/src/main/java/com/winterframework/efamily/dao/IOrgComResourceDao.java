package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.OrgResource;

public interface IOrgComResourceDao extends IBaseDao<OrgResource>{ 

	public OrgResource getByResourceId(String resourceId) throws Exception;
	
}
