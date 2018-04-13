package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EfResourceAssist;
import com.winterframework.efamily.entity.FmlResource;

public interface IEfResourceDao extends IResourceDao{

	public List<FmlResource> getResourceByAssistantTypeAndStatus(EfResourceAssist efResourceAssist)throws BizException;
}
