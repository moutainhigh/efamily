package com.winterframework.efamily.server.handler;


import org.springframework.stereotype.Service;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.OpenMonitorRequest;
import com.winterframework.efamily.dto.OpenMonitorResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

	@Service("openMonitorHandler")
	public class OpenMonitorHandler extends AbstractHandler{
		@PropertyConfig("server.url.app")
		private String serverUrl;
		@PropertyConfig("app.server.open_monitor")
		private String urlPath;
		
		
		@Override
		protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
			OpenMonitorRequest  bizReqList =  new OpenMonitorRequest();
			bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
			bizReqList.setDeviceId(Long.valueOf(request.getData().get("deviceId")));
			bizReqList.setPhoneNumber(request.getData().get("phoneNumber"));
			Response<OpenMonitorResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,OpenMonitorResponse.class);
			FmlResponse res=new FmlResponse(request);
	        if(null!=bizRes){			
				res.setStatus(bizRes.getStatus().getCode()); 
			}else{
				res.setStatus(StatusCode.UNKNOW.getValue());
			}
			return res;
		}
	}
