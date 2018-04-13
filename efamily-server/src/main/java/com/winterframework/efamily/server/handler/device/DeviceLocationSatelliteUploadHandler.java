package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceLocationSatelliteRequest;
import com.winterframework.efamily.dto.device.DeviceLocationSatelliteResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备定位星信噪比上传处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("deviceLocationSatelliteUploadHandler")
public class DeviceLocationSatelliteUploadHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.location_satellite_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {	
		List<DeviceLocationSatelliteRequest> bizData=JsonUtils.fromJson2List(req.getValue("data"), DeviceLocationSatelliteRequest.class);
		FmlResponse res=new FmlResponse(req);
		Response<DeviceLocationSatelliteResponse> bizRes=http(serverUrl,urlPath,ctx,bizData,DeviceLocationSatelliteResponse.class);	
		res=getFmlResponse(res,bizRes);
		return res;
	}
	
}