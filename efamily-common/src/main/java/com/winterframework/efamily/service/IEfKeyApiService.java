package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfKeyApi;


public interface IEfKeyApiService extends IBaseService<EfKeyApi> {
 
	EfKeyApi getByKey(String key) throws BizException;
}
