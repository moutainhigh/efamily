package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceChatMessageRequest;
import com.winterframework.efamily.dto.device.DeviceChatMessageResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备聊天处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("deviceChatHandler")
public class DeviceChatHandler extends AbstractHandler{ 
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.chat_receive")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		DeviceChatMessageRequest bizReq=JsonUtils.fromJson(req.getValue("data"), DeviceChatMessageRequest.class); 
		
		Response<DeviceChatMessageResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceChatMessageResponse.class);	
		FmlResponse res=new FmlResponse(req);
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}