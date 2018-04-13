package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceHardwareRequest;
import com.winterframework.efamily.dto.device.DeviceHardwareResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 设备向服务器上传硬件信息
 * 
 * @ClassName
 * @Description
 * @author ibm 2015年3月17日
 */
@Service("deviceHardwareHandler")
public class DeviceHardwareHandler extends AbstractHandler {
	@PropertyConfig("server.url.device")
	private String serverUrl;
	@PropertyConfig("device.hardware")
	private String urlPath;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException{ 
		DeviceHardwareRequest bizReq=(DeviceHardwareRequest)JsonUtils.fromJson(req.getValue("data"), DeviceHardwareRequest.class);
		FmlResponse res=new FmlResponse(req); 
		Response<DeviceHardwareResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceHardwareResponse.class);	
		res=getFmlResponse(res,bizRes);
		return res;
	}
}