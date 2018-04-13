package com.winterframework.efamily.server.handler.device;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.device.DeviceParamRequest;
import com.winterframework.efamily.dto.device.DeviceParamResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备最大亮度查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的真实参数值 写入设备参数表  真实值字段
 * 任务定时更新设备参数值
 */
@Service("deviceParamGetHandler")
public class DeviceParamGetHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.param_get")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Long time=Long.valueOf(req.getValue("time"));
		int code=Integer.valueOf(req.getValue("code"));
		String value=req.getValue("value");
		
		log.info("response time:"+new Date(time));
		 
		FmlResponse res=new FmlResponse(req);
		DeviceParamRequest bizReq=new DeviceParamRequest();
		bizReq.setCode(code);
		bizReq.setValue(value);
		Response<DeviceParamResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceParamResponse.class);	
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}