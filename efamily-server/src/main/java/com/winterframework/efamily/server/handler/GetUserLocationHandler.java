package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.GetUserLocationRequest;
import com.winterframework.efamily.dto.GetUserLocationResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;


@Service("getUserLocationHandler")
public class GetUserLocationHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.get_user_location")
	private String urlPath;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		GetUserLocationRequest  bizReqList =  new GetUserLocationRequest();
		bizReqList.setDeviceId(Long.valueOf(request.getData().get("deviceId")));
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		//bizReqList.setTime(Long.valueOf(request.getValue("time")));
		bizReqList.setTime(DateUtils.getCurTime());
		Response<GetUserLocationResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetUserLocationResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("userId", bizRes.getData().getUserId()+"");
			   responseMap.put("deviceId", bizRes.getData().getDeviceId()+"");
			   responseMap.put("lat", bizRes.getData().getLat());
			   responseMap.put("lng", bizRes.getData().getLng());
			   responseMap.put("addressDesc", bizRes.getData().getAddressDesc());
			   responseMap.put("precision", bizRes.getData().getPrecision());
			   responseMap.put("time", bizRes.getData().getTime());
			   responseMap.put("needWait", bizRes.getData().getNeedWait()+"");
			   responseMap.put("type", bizRes.getData().getType()+"");
			   responseMap.put("batteryInfo", bizRes.getData().getBatteryInfo());
			   responseMap.put("runningMode", bizRes.getData().getRunningMode()+"");
			   responseMap.put("onLineStatus", bizRes.getData().getOnLineStatus()+"");
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}

