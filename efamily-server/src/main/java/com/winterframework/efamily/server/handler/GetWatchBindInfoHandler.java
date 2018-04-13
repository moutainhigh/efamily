package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetWatchBindInfoRequest;
import com.winterframework.efamily.dto.GetWatchBindInfoResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("getWatchBindInfoHandler")
public class GetWatchBindInfoHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.get_watch_bindInfo")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		GetWatchBindInfoRequest  bizReqList =  new GetWatchBindInfoRequest();
		bizReqList.setDeviceCode(request.getData().get("deviceCode"));
		Response<GetWatchBindInfoResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetWatchBindInfoResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("bindWatchStatus", bizRes.getData().getBindWatchStatus());
			   responseMap.put("phoneNumber", bizRes.getData().getPhoneNumber());
			   responseMap.put("familyCode", bizRes.getData().getFamilyCode());
			   responseMap.put("familyName", bizRes.getData().getFamilyName());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}