package com.winterframework.efamily.server.handler;

import org.springframework.stereotype.Service;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.ManageAttentionRequest;
import com.winterframework.efamily.dto.ManageAttentionResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("manageAttentionHandler")
public class ManageAttentionHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.manage_attention")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		ManageAttentionRequest  bizReqList =  new ManageAttentionRequest();
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		bizReqList.setDeviceCode(request.getData().get("deviceCode"));
		bizReqList.setOprType(Long.valueOf(request.getData().get("oprType")));
		
		Response<ManageAttentionResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,ManageAttentionResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
