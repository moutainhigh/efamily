package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceMobileRequest;
import com.winterframework.efamily.dto.device.DeviceMobileResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备移动信息查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("deviceMobileUploadHandler")
public class DeviceMobileUploadHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.mobile_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {	 
		List<DeviceMobileRequest> bizReqList=JsonUtils.fromJson2List(req.getValue("data"), DeviceMobileRequest.class);  
		FmlResponse res=new FmlResponse(req);
		if(null!=bizReqList && bizReqList.size()>0){
			Response<DeviceMobileResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,DeviceMobileResponse.class);	
			res=getFmlResponse(res,bizRes);
		}
		return res;
	}
	
}