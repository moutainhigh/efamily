package com.winterframework.efamily.server.handler.device;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
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
 * @author ibm
 * 2016年3月3日
 */
@Service("deviceSittingQueryHandler")
public class DeviceSittingQueryHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.sitting_upload")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Long time = Long.valueOf(req.getValue("time"));
		log.debug("sitting query response time:"+DateUtils.getDate(time));
		List<Long> toTimeList = JsonUtils.fromJson2List(req.getValue("data"), Long.class);
		FmlResponse res=new FmlResponse(req);
		if(null!=toTimeList && toTimeList.size()>0){
			DeviceSittingRequest bizReq=new DeviceSittingRequest();
			bizReq.setToTimeList(toTimeList);
			Response<DeviceSittingResponse> bizRes = http(serverUrl,urlPath, ctx, bizReq, DeviceSittingResponse.class);
			res = getFmlResponse(res, bizRes);
		}else{
			log.info("sitting query data is empty.");
		}
		return res;
	}
	
}