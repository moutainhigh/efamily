package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DevicePowerAutoRequest;
import com.winterframework.efamily.dto.device.DevicePowerAutoResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备自动开关机设置查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("devicePowerAutoQueryHandler")
public class DevicePowerAutoQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.power_auto_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Long time=Long.valueOf(req.getValue("time"));
		DevicePowerAutoRequest bizData=JsonUtils.fromJson(req.getValue("data"), DevicePowerAutoRequest.class) ;
		log.info("response time:"+DateUtils.getDate(time));
		Response<DevicePowerAutoResponse> bizRes=http(serverUrl,urlPath,ctx,bizData,DevicePowerAutoResponse.class);	
		FmlResponse res=new FmlResponse(req);
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}