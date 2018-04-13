package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;

import com.winterframework.efamily.dto.device.DeviceLunarRequest;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备心率查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 

 */
@Service("deviceLunarQueryHandler")
public class DeviceLunarQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.lunar_query")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long time=Long.valueOf(req.getValue("time"));
		log.info("response time:"+DateUtils.getDate(time)); 
		FmlResponse res=new FmlResponse(req);
		Response<DeviceLunarRequest> bizRes=http(serverUrl,urlPath,ctx,time,DeviceLunarRequest.class);	
		res=getFmlResponse(res,bizRes);
		if(res.getStatus()==StatusCode.OK.getValue()&&bizRes.getData()!=null){
			res.setValue("data", JsonUtils.toJson(bizRes.getData()));
		}
		return res;
	}
	
}