package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfKey;


public interface IEfKeyService extends IBaseService<EfKey> {
 
	EfKey getByKey(String key) throws BizException;
}
