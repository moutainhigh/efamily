package com.winterframework.efamily.server.handler.device;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceSittingRequest;
import com.winterframework.efamily.dto.device.DeviceSittingResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备久坐时间查询处理类
 * @ClassName
 * @Description
 * @author denny
 * 2015年11月23日
 * 
 * 设备久坐时间数据入库 
 */
@Service("deviceSittingUploadHandler")
public class DeviceSittingUploadHandler extends AbstractHandler{
	@PropertyConfig("device.sitting_upload")
	private String urlPath;
	
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Map<String, String> data = req.getData();
		List<Long> toTimeList = JsonUtils.fromJson2List(data.get("data"), Long.class);
		FmlResponse res=new FmlResponse(req);
		if(null!=toTimeList && toTimeList.size()>0){
			DeviceSittingRequest bizReq=new DeviceSittingRequest();
			bizReq.setToTimeList(toTimeList);
			Response<DeviceSittingResponse> bizRes = http(serverUrl,urlPath, ctx, bizReq, DeviceSittingResponse.class);
			res = getFmlResponse(res, bizRes);
		}
		return res;
	}
	
}