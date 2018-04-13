package com.winterframework.efamily.api.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfKeyIp;

public interface IEfKeyIpService extends IBaseService<EfKeyIp>{

	 public void saveOrUpdateKeyIpBy(Context ctx,String key,String ip) throws BizException;
	 
}