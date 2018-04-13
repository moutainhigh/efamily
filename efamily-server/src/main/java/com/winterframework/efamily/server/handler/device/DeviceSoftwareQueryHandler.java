package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.device.DeviceSoftwareRequest;
import com.winterframework.efamily.dto.device.DeviceSoftwareResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备软件查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("deviceSoftwareQueryHandler")
public class DeviceSoftwareQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.software_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {	 
		Long time=Long.valueOf(req.getValue("time"));
		DeviceSoftwareRequest bizData=new DeviceSoftwareRequest();
		bizData.setVersion(req.getValue("version"));
		
		log.info("response time:"+DateUtils.getDate(time));
		FmlResponse res=new FmlResponse(req);
		Response<DeviceSoftwareResponse> bizRes=http(serverUrl,urlPath,ctx,bizData,DeviceSoftwareResponse.class);	
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}