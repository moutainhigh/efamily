package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.FmlResource;

public interface IEfResourceService extends IBaseService<FmlResource>{

	public List<FmlResource> getResourceByAssistantTypeAndStatus(int type,int status)throws BizException;
	
	public FmlResource getRandResourceByAssistantTypeAndStatus(int type,int status) throws BizException;
}
