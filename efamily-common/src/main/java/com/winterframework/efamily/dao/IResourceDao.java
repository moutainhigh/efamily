package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.FmlResource;

public interface IResourceDao  extends IBaseDao<FmlResource>{ 
	FmlResource getById(String id) throws Exception;
}
