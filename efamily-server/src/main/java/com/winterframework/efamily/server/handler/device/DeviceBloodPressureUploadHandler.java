package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.BloodPressure;
import com.winterframework.efamily.dto.HeartResponse;
import com.winterframework.efamily.dto.device.DeviceBloodPressureRequest;
import com.winterframework.efamily.dto.device.DeviceBloodPressureResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备心率上传处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的心率数据入库 
 */
@Service("deviceBloodPressureUploadHandler")
public class DeviceBloodPressureUploadHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.bloodPressure_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		List<DeviceBloodPressureRequest> bizReqList=JsonUtils.fromJson2List(req.getValue("data"), DeviceBloodPressureRequest.class) ; 
		FmlResponse res=new FmlResponse(req);
		if(null!=bizReqList && bizReqList.size()>0){
			Response<DeviceBloodPressureResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,DeviceBloodPressureResponse.class);	
			res=getFmlResponse(res,bizRes); 
		}
		return res;
	}
	
}