package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.device.DeviceHelloNewRequest;
import com.winterframework.efamily.dto.device.DeviceHelloNewResponse;
import com.winterframework.efamily.dto.device.DeviceHelloRequest;
import com.winterframework.efamily.dto.device.DeviceHelloResponse;
import com.winterframework.efamily.entity.EjlUserLoginRecord;

public interface IDeviceHelloService{
	
	/**
	 * 手表打招呼
	 * @param bizReq
	 * @throws Exception
	 */
	DeviceHelloResponse hello(Context ctx,DeviceHelloRequest bizReq,String token) throws BizException;
	DeviceHelloNewResponse helloNew(Context ctx,DeviceHelloNewRequest bizReq) throws BizException;
	
	/**
	 * 登录日志
	 * @param ctx
	 * @param loginRecord
	 * @throws BizException
	 */
	void loginRecord(Context ctx,EjlUserLoginRecord loginRecord) throws BizException;
}
