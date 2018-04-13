package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.GetUserHealthlyConfigRequest;
import com.winterframework.efamily.dto.HealthyUsersResponse;
import com.winterframework.efamily.dto.UserHealthlyConfigStruc;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("getHealthyUserConfigHandler")
public class GetHealthyUserConfigHandler extends AbstractHandler{

	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.getHealthyUserConfig")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		
		GetUserHealthlyConfigRequest queryRes = new GetUserHealthlyConfigRequest();
		queryRes.setDeviceId(Long.valueOf(request.getData().get("deviceId")));
		queryRes.setUserId(Long.valueOf(request.getData().get("userId")));
		Response<UserHealthlyConfigStruc> bizRes=http(serverUrl,urlPath,ctx,queryRes,UserHealthlyConfigStruc.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("healthyUserConfig", JsonUtils.toJson(bizRes.getData()));
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
