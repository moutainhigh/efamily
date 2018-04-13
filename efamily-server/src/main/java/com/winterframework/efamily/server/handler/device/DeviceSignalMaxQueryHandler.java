package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.device.DeviceParamRequest;
import com.winterframework.efamily.dto.device.DeviceParamResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备信号最大值查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的信号数据入库 
 */
@Deprecated	//调用参数接口
@Service("deviceSignalMaxQueryHandler")
public class DeviceSignalMaxQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.param_get")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long time=Long.valueOf(req.getValue("time"));
		int value=Integer.valueOf(req.getValue("value"));
		log.info("response time:"+DateUtils.getDate(time));
		DeviceParamRequest bizReq=new DeviceParamRequest();
		bizReq.setCode(400009);	//signal_max	//改成手表调用参数接口？
		bizReq.setValue(value+"");
		 
		Response<DeviceParamResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceParamResponse.class);	
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}