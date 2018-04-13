package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.UserLocationRequest;
import com.winterframework.efamily.dto.UserLocationResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取用户定位handler
 * 
 * @author david
 * 
 */
@Service("userLocationHandler")
public class UserLocationHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.user_location")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {	
		UserLocationRequest  bizReqList =  new UserLocationRequest();
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		bizReqList.setWatchId(Long.valueOf(request.getData().get("watchId")));
		bizReqList.setRecentHourType(Long.valueOf(request.getData().get("recentHourType")));
		bizReqList.setQueryType(Long.valueOf(request.getData().get("queryType")));
		
		Response<UserLocationResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,UserLocationResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData() != null){
				responseMap.put("location", bizRes.getData().getLocation());
				responseMap.put("batteryInfo", bizRes.getData().getBatteryInfo());
			}
			
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
	
	
}
