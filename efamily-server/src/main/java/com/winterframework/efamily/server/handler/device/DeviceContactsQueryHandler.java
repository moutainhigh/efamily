package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceContactsRequest;
import com.winterframework.efamily.dto.device.DeviceContactsResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备通讯录查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的通讯录数据入库 
 */
@Service("deviceContactsQueryHandler")
public class DeviceContactsQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.contacts_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long time=Long.valueOf(req.getValue("time"));
		List<DeviceContactsRequest> bizReqList=JsonUtils.fromJson2List(req.getValue("data"), DeviceContactsRequest.class) ;
		
		log.info("response time:"+DateUtils.getDate(time));
		FmlResponse res=new FmlResponse(req);
		Response<DeviceContactsResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,DeviceContactsResponse.class);	
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}