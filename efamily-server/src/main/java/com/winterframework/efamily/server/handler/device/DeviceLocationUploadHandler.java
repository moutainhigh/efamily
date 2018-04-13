package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.LocationRequest;
import com.winterframework.efamily.dto.LocationResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备定位上传处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的定位数据入库 
 */
@Service("deviceLocationUploadHandler")
public class DeviceLocationUploadHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.location_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 	
		List<LocationRequest> bizReqList=JsonUtils.fromJson2List(req.getValue("data"), LocationRequest.class) ;
		FmlResponse res=new FmlResponse(req);
		if(null!=bizReqList && bizReqList.size()>0){
			Response<LocationResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,LocationResponse.class);	
			res=getFmlResponse(res,bizRes); 
		}
		return res;
	}
	
}