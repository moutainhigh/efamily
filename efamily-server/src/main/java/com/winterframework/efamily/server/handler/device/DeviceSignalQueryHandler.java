package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceSignalRecordRequest;
import com.winterframework.efamily.dto.device.DeviceSignalRecordResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备信号数据查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的信号数据入库 
 */
@Service("deviceSignalQueryHandler")
public class DeviceSignalQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.signal_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long time=Long.valueOf(req.getValue("time"));
		List<DeviceSignalRecordRequest> bizReqList=JsonUtils.fromJson2List(req.getValue("data"), DeviceSignalRecordRequest.class) ;
		
		log.info("response time:"+DateUtils.getDate(time));
		FmlResponse res=new FmlResponse(req); 
		if(null!=bizReqList && bizReqList.size()>0){
			Response<DeviceSignalRecordResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,DeviceSignalRecordResponse.class);
			res=getFmlResponse(res,bizRes);
		}
		return res;
	}
	
}