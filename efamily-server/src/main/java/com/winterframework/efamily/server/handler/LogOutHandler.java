package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.LogOutRequest;
import com.winterframework.efamily.dto.LogOutResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 登陆handler
 * @author david
 *
 */
@Service("logOutHandler")
public class LogOutHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.log_out")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		Long userId=ctx.getUserId();
		Long deviceId=ctx.getDeviceId();
/*		LogOutRequest  bizReqList =  new LogOutRequest();
		bizReqList.setToken(TokenManager.getToken(userId, deviceId));
		Response<LogOutResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,LogOutResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			TokenManager.remove(request.getToken());
			ChannelManager.remove(userId,deviceId);
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}*/
		FmlResponse res=new FmlResponse(request);
		res.setStatus(StatusCode.OK.getValue()); 
		Map<String,String> responseMap = new HashMap<String,String>();
		TokenManager.remove(request.getToken());
		ChannelManager.remove(userId,deviceId);
		res.setData(responseMap);
		return res;
	}
}
