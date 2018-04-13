package com.winterframework.efamily.api.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;

public interface IApiAccessManageService {
	public void apiAccessManage(String ukey,String url) throws BizException;
}
