package com.winterframework.efamily.server.handler.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.app.AppDeviceHealthHeartLatestRequest;
import com.winterframework.efamily.dto.app.AppDeviceHealthHeartLatestResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 

/**
 * 设备心率最新数据查询
 * @ClassName
 * @Description
 * @author ibm
 * 2016年10月27日
 */
@Service("appDeviceHealthHeartLatestHandler")
public class DeviceHealthHeartLatestHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.device.health.heart.latest")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long userId=Long.valueOf(req.getValue("userId"));
		Long deviceId=Long.valueOf(req.getValue("deviceId"));
		
		AppDeviceHealthHeartLatestRequest bizReq=new AppDeviceHealthHeartLatestRequest();
		bizReq.setUserId(userId);
		bizReq.setDeviceId(deviceId);
		
		FmlResponse res=new FmlResponse(req);
		Response<AppDeviceHealthHeartLatestResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,AppDeviceHealthHeartLatestResponse.class);
		
		res=getFmlResponse(res,bizRes);
		if(null!=bizRes && null!=bizRes.getData()){ 
			Map<String,String> resData=new HashMap<String,String>();
			resData.put("value",bizRes.getData().getValue()==null?"":bizRes.getData().getValue()+"");
			res.setData(resData);
		}
		
		return res;
	}
	
}