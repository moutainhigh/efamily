package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.DeviceOperationRequest;

public interface IDeviceOperationService extends IEfDeviceOperationService{
	
	
	/**
	 * 设备关键操作服务
	 * @param ctx
	 * @param bizReq
	 * @throws BizException
	 */
	void operation(Context ctx,DeviceOperationRequest bizReq) throws BizException;
}
