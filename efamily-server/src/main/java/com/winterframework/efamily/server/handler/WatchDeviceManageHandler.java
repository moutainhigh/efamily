package com.winterframework.efamily.server.handler;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.WatchDeviceManageRequest;
import com.winterframework.efamily.dto.WatchDeviceManageResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 设置手表的参数  handler
 * @author david
 *
 */
@Service("watchDeviceManageHandler")
public class WatchDeviceManageHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.watch_device_manage")
	private String urlPath;
	private static final String APP_DEVICE_UN_BIND = "100016" ;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		WatchDeviceManageRequest  bizReqList =  new WatchDeviceManageRequest();
		bizReqList.setWatchId(request.getData().get("watchId"));
		bizReqList.setParameterIndex(request.getData().get("parameterIndex"));
		bizReqList.setParameterContext(request.getData().get("parameterContext"));
		bizReqList.setFamilyId(request.getData().get("familyId"));
		bizReqList.setOprType(Long.valueOf(request.getData().get("oprType")));
		Response<WatchDeviceManageResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,WatchDeviceManageResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("watchId", bizRes.getData().getWatchId());
				responseMap.put("parameterIndex", bizRes.getData().getParameterIndex());
				responseMap.put("parameterContext", bizRes.getData().getParameterContext());
				responseMap.put("userId", bizRes.getData().getUserId());
				responseMap.put("familyId", bizRes.getData().getFamilyId());
			}
			res.setData(responseMap);
			
			//*********** 解绑手表时 需要清空掉 token *****************************
			if(bizRes.getStatus().getCode() == StatusCode.OK.getValue() && request.getData().get("parameterIndex").equals(APP_DEVICE_UN_BIND)){
				log.info("解绑手表，清除TOKEN , CHANNEL ： userId = "+bizRes.getData().getUsingDeviceUserId()+" ; watchId = "+bizRes.getData().getWatchId()+" ; ");
				TokenManager.remove(Long.valueOf(bizRes.getData().getUsingDeviceUserId()), Long.valueOf(bizRes.getData().getWatchId()));
				ChannelManager.remove(Long.valueOf(bizRes.getData().getUsingDeviceUserId()), Long.valueOf(bizRes.getData().getWatchId()));
			}
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
	
	
	
}
