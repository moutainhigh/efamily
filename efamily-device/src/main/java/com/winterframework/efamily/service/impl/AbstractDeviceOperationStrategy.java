package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.DeviceOperationRequest;
import com.winterframework.efamily.service.IEfDeviceOperationService;

public abstract class AbstractDeviceOperationStrategy {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Resource(name = "efDeviceOperationServiceImpl")
    private IEfDeviceOperationService efDeviceOperationService;
	
	
	public void doProcess(Context ctx, DeviceOperationRequest req) throws BizException{
		doBiz(ctx,req.getCode(),req.getTime(),req.getStatus());
	}
	abstract protected void doBiz(Context ctx,Integer code,Long time,Integer status) throws BizException;
}
