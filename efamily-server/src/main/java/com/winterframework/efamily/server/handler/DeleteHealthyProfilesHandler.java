package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.DeleteHealthyProfilesRequest;
import com.winterframework.efamily.dto.DeleteHealthyProfilesResponse;
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
@Service("deleteHealthyProfilesHandler")
public class DeleteHealthyProfilesHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.delete_healthy_profiles")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		DeleteHealthyProfilesRequest  bizReqList =  new DeleteHealthyProfilesRequest();
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		bizReqList.setDeviceId(Long.valueOf(request.getData().get("deviceId")));
		Response<DeleteHealthyProfilesResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,DeleteHealthyProfilesResponse.class);
		FmlResponse res=new FmlResponse(request);
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
