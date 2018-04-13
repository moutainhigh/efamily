package com.winterframework.efamily.server.handler.device;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceChatSettingRequest;
import com.winterframework.efamily.dto.device.DeviceChatSettingResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备聊天设置查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("deviceChatQueryHandler")
public class DeviceChatQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl;
	@PropertyConfig("device.chat_setting_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long time=Long.valueOf(req.getValue("time"));
		log.info("response time:"+DateUtils.getDate(time));
		DeviceChatSettingRequest bizReq=JsonUtils.fromJson(req.getValue("data"), DeviceChatSettingRequest.class) ;
		
		FmlResponse res=new FmlResponse(req);
		Response<DeviceChatSettingResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceChatSettingResponse.class);	
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}