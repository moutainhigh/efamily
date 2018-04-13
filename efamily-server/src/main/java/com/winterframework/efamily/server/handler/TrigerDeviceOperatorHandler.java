package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.TrigerDeviceRequest;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("trigerDeviceOperatorHandler")
public class TrigerDeviceOperatorHandler extends AbstractHandler{

	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.trigerDeviceOperator")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req)
			throws ServerException {
		TrigerDeviceRequest trigerDeviceRequest = new TrigerDeviceRequest();
		trigerDeviceRequest.setType(Integer.valueOf(req.getValue("opType")));
		trigerDeviceRequest.setContinueTime(req.getValue("continueTime")==null?null:Double.valueOf(req.getValue("continueTime")));
		trigerDeviceRequest.setUserId(Long.valueOf(req.getValue("userId")));
		trigerDeviceRequest.setStatus(req.getValue("status")==null?null:Integer.valueOf(req.getValue("status")));
		trigerDeviceRequest.setDeviceId(req.getValue("deviceId")==null?null:Long.valueOf(req.getValue("deviceId")));
		Response<TrigerDeviceRequest> bizRes=http(serverUrl,urlPath,ctx,trigerDeviceRequest,TrigerDeviceRequest.class);
		FmlResponse res=new FmlResponse(req);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
	
}
