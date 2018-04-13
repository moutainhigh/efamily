package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.DeviceOperationRequest;
import com.winterframework.efamily.dto.DeviceOperationResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 

/**
 * 设备关键操作
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月3日
 */
@Service("deviceOperationHandler")
public class DeviceOperationHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.operation")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Integer code=Integer.valueOf(req.getValue("code"));
		Integer status=Integer.valueOf(req.getValue("status"));
		Long operTime=Long.valueOf(req.getValue("time"));
		 
		DeviceOperationRequest bizReq=new DeviceOperationRequest();
		bizReq.setCode(code);
		bizReq.setTime(operTime);
		bizReq.setStatus(status);
		FmlResponse res = new FmlResponse(req);
		Response<DeviceOperationResponse> bizRes = http(serverUrl,urlPath, ctx, bizReq, DeviceOperationResponse.class);
		res = getFmlResponse(res, bizRes);
		
		return res;
	}
	
}