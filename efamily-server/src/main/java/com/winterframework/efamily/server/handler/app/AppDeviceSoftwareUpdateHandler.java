package com.winterframework.efamily.server.handler.app;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareUpdateRequest;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareUpdateResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 

/**
 * 设备软件版本升级
 * @ClassName
 * @Description
 * @author ibm
 * 2016年10月27日
 */
@Service("appDeviceSoftwareUpdateHandler")
public class AppDeviceSoftwareUpdateHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.device.software_update")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long deviceId=Long.valueOf(req.getValue("deviceId"));
		
		AppDeviceSoftwareUpdateRequest bizReq=new AppDeviceSoftwareUpdateRequest();
		bizReq.setDeviceId(deviceId);
		
		FmlResponse res=new FmlResponse(req);
		Response<AppDeviceSoftwareUpdateResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,AppDeviceSoftwareUpdateResponse.class);
		
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}