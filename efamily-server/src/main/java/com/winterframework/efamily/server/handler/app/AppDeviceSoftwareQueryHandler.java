package com.winterframework.efamily.server.handler.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareQueryRequest;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareQueryResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 

/**
 * 查询设备软件版本
 * @ClassName
 * @Description
 * @author ibm
 * 2016年10月27日
 */
@Service("appDeviceSoftwareQueryHandler")
public class AppDeviceSoftwareQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.device.software_query")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long deviceId=Long.valueOf(req.getValue("deviceId"));
		
		AppDeviceSoftwareQueryRequest bizReq=new AppDeviceSoftwareQueryRequest();
		bizReq.setDeviceId(deviceId);
		
		FmlResponse res=new FmlResponse(req);
		Response<AppDeviceSoftwareQueryResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,AppDeviceSoftwareQueryResponse.class);
		
		res=getFmlResponse(res,bizRes);
		if(null!=bizRes && null!=bizRes.getData()){ 
			Map<String,String> resData=new HashMap<String,String>();
			resData.put("hasNew",bizRes.getData().getHasNew()+"");
			resData.put("version",bizRes.getData().getVersion()+"");
			res.setData(resData);
		}
		return res;
	}
	
}