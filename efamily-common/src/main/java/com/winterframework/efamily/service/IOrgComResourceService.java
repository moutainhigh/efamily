package com.winterframework.efamily.service;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.OrgResource;

public interface IOrgComResourceService extends IBaseService<OrgResource> { 

	public OrgResource getByResourceId(String resourceId) throws Exception;
}
