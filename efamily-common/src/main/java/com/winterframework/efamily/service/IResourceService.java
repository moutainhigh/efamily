package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.FmlResource;

public interface IResourceService extends IBaseService<FmlResource> { 
	FmlResource getById(String id) throws BizException;
}
