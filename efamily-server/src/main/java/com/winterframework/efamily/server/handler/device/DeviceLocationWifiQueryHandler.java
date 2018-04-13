package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.LocationMobileRequest;
import com.winterframework.efamily.dto.LocationMobileResponse;
import com.winterframework.efamily.dto.LocationRequest;
import com.winterframework.efamily.dto.LocationWifiRequest;
import com.winterframework.efamily.dto.LocationWifiResponse;
import com.winterframework.efamily.dto.device.DeviceMobileRequest;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备定位wifi查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的定位数据入库 
 */
@Service("deviceLocationWifiQueryHandler")
public class DeviceLocationWifiQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.location_wifi_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException { 
		Long time=Long.valueOf(req.getValue("time")); 
		LocationMobileRequest bizReq=new LocationMobileRequest();
		if(null!=req.getValue("data")){
			bizReq.setWifiList(JsonUtils.fromJson2List(req.getValue("data"), LocationWifiRequest.class));			
		}else{
			bizReq.setWifiList(JsonUtils.fromJson2List(req.getValue("wifi"), LocationWifiRequest.class));
			bizReq.setMobileList(JsonUtils.fromJson2List(req.getValue("mobile"), DeviceMobileRequest.class));
			if(null!=req.getValue("gps")){
				bizReq.setGpsList(JsonUtils.fromJson2List(req.getValue("gps"), LocationRequest.class));
			}
		}
		
		log.info("response time:"+DateUtils.getDate(time));
		FmlResponse res=new FmlResponse(req); 
		if(null!=bizReq){
			Response<LocationMobileResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,LocationMobileResponse.class);	
			res=getFmlResponse(res,bizRes);
		}
		return res;
	}
	
}