package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.device.DeviceHeartFinishRequest;
import com.winterframework.efamily.dto.device.DeviceHeartFinishResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备心率测量完成
 * @ClassName
 * @Description
 * @author ibm 
 * 
 */
@Service("deviceHeartFinishHandler")
public class DeviceHeartFinishHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.health.heart.finish")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		DeviceHeartFinishRequest bizReq=new DeviceHeartFinishRequest();
		bizReq.setStatus(req.getStatus());
		bizReq.setTime(Long.valueOf(req.getValue("time")));
		
		FmlResponse res=new FmlResponse(req);
		Response<DeviceHeartFinishResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceHeartFinishResponse.class);	
		res=getFmlResponse(res,bizRes); 
		return res;
	}
	
}