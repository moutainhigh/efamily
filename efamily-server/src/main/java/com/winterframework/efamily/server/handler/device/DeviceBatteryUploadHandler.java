/**   
* @Title: DeviceBatteryQueryHandler.java 
* @Package com.winterframework.efamily.server.handler.device 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-6 下午1:22:34 
* @version V1.0   
*/
package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceBatteryRecordRequest;
import com.winterframework.efamily.dto.device.DeviceBatteryRecordResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: DeviceBatteryQueryHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-6 下午1:22:34 
*  
*/
@Service("deviceBatteryUploadHandler")
public class DeviceBatteryUploadHandler extends AbstractHandler {
	@PropertyConfig("server.url.device")
	private String serverUrl;
	
	@PropertyConfig("device.battery_query")
	private String urlPath;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		List<DeviceBatteryRecordRequest> bizReqList=JsonUtils.fromJson2List(req.getValue("data"), DeviceBatteryRecordRequest.class); 
		
		Response<DeviceBatteryRecordResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,DeviceBatteryRecordResponse.class);	
		FmlResponse res=new FmlResponse(req);
		res=getFmlResponse(res,bizRes); 
		return res;
	}

}
