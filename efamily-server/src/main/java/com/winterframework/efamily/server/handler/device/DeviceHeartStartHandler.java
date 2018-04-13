package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.device.DeviceHeartStartRequest;
import com.winterframework.efamily.dto.device.DeviceHeartStartResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备对心率测量的响应
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("deviceHeartStartHandler")
public class DeviceHeartStartHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.health.heart.start")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		DeviceHeartStartRequest bizReq=new DeviceHeartStartRequest();
		bizReq.setStatus(req.getStatus());
		bizReq.setTime(Long.valueOf(req.getValue("time")));
		
		FmlResponse res=new FmlResponse(req);
		Response<DeviceHeartStartResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceHeartStartResponse.class);	
		res=getFmlResponse(res,bizRes); 
		if(res.getStatus()==200017){	//设备不支持该功能
			return null;
		}
		return res;
	}
	
}