package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;

public interface IDeviceCommonService{
	void uploadTime(Context ctx,Long time) throws BizException;
}
