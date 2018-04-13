package com.winterframework.efamily.server.handler.app;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.app.AppDeviceHealthHeartMeasureRequest;
import com.winterframework.efamily.dto.app.AppDeviceHealthHeartMeasureResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 

/**
 * 设备心率测量开始
 * @ClassName
 * @Description
 * @author ibm
 * 2016年10月27日
 */
@Service("appDeviceHealthHeartMeasureHandler")
public class AppDeviceHealthHeartMeasureHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.device.health.heart.measure")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long userId=Long.valueOf(req.getValue("userId"));
		Long deviceId=Long.valueOf(req.getValue("deviceId"));
		Integer type=Integer.valueOf(req.getValue("type"));
		
		AppDeviceHealthHeartMeasureRequest bizReq=new AppDeviceHealthHeartMeasureRequest();
		bizReq.setUserId(userId);
		bizReq.setDeviceId(deviceId);
		bizReq.setType(type);
		
		FmlResponse res=new FmlResponse(req);
		Response<AppDeviceHealthHeartMeasureResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,AppDeviceHealthHeartMeasureResponse.class);
		
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}