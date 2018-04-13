package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.HealthyProfilesRequest;
import com.winterframework.efamily.dto.HealthyProfilesResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Service("healthyProfilesHandler")
public class HealthyProfilesHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.healthy_profiles")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		HealthyProfilesRequest  bizReqList =  new HealthyProfilesRequest();
		bizReqList.setDeviceId(request.getData().get("deviceId")==null?null:Long.valueOf(request.getData().get("deviceId")));
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		Response<HealthyProfilesResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,HealthyProfilesResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("healthyProfile", bizRes.getData().getHealthyProfileList());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
