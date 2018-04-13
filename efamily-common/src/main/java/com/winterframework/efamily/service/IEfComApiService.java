package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfApi;

public interface IEfComApiService extends IBaseService<EfApi> {

	public EfApi getByUrl(Context ctx,String url) throws BizException;
}
