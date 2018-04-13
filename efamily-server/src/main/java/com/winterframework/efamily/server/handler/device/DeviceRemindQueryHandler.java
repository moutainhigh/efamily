package com.winterframework.efamily.server.handler.device;

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

 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("deviceRemindQueryHandler")
public class DeviceRemindQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.remind_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		DeviceChatSettingRequest bizReq=JsonUtils.fromJson(req.getValue("data"), DeviceChatSettingRequest.class) ;
		log.info("20402 response time:"+DateUtils.getDate(DateUtils.currentDate())+":"+req.getValue("data"));
		FmlResponse res=new FmlResponse(req);
		Response<DeviceChatSettingResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceChatSettingResponse.class);	
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}