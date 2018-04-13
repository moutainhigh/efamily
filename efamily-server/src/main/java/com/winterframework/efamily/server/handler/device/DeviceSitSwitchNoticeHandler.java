package com.winterframework.efamily.server.handler.device;


import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.SleepRequest;
import com.winterframework.efamily.dto.SleepResponse;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备睡眠上传处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的睡眠数据入库 
 */
@Service("deviceSitSwitchNoticeHandler")
public class DeviceSitSwitchNoticeHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.sitSwitch_notice")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		FmlResponse res=new FmlResponse(req);
		res.setStatus(StatusCode.OK.getValue());
		//Response<StepResponse> bizRes=http(serverUrl,urlPath,ctx,Integer.valueOf(req.getValue("status")),StepResponse.class);	
		//res=getFmlResponse(res,bizRes);
		return res;
	}
}