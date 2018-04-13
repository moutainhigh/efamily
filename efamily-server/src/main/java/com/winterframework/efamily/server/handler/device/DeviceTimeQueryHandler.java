package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.device.DeviceTimeRequest;
import com.winterframework.efamily.dto.device.DeviceTimeResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备时间查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("deviceTimeQueryHandler")
public class DeviceTimeQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.time_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {	
		Long time=Long.valueOf(req.getValue("time"));
		DeviceTimeRequest bizData=new DeviceTimeRequest();
		bizData.setTime(time);
		
		log.info("response time:"+DateUtils.getDate(time));
		FmlResponse res=new FmlResponse(req);
		Response<DeviceTimeResponse> bizRes=http(serverUrl,urlPath,ctx,bizData,DeviceTimeResponse.class);	
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}