package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.UserNoticeStrucRequest;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ejlUserNoticeSaveHandler")
public class EjlUserNoticeSaveHandler extends AbstractHandler{

	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.saveEjlUserNotice")
	private String urlPath;
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req)
			throws ServerException {
		FmlResponse res=new FmlResponse(req);
		UserNoticeStrucRequest request = new UserNoticeStrucRequest();
		request.setDeviceUserId(Long.valueOf(req.getData().get("deviceUserId")));
		request.setBloodStatus(req.getData().get("bloodStatus")==null?null:Integer.valueOf(req.getData().get("bloodStatus")));
		request.setDiastolicRangeGt(req.getData().get("diastolicRangeGt")==null?null:Integer.valueOf(req.getData().get("diastolicRangeGt")));
		request.setDiastolicRangeLt(req.getData().get("diastolicRangeLt")==null?null:Integer.valueOf(req.getData().get("diastolicRangeLt")));
		request.setId(req.getData().get("id")==null||req.getData().get("id").equals("-1")?null:Long.valueOf(req.getData().get("id")));
		request.setRateRangeGt(req.getData().get("rateRangeGt")==null?null:Integer.valueOf(req.getData().get("rateRangeGt")));
		request.setRateRangeLt(req.getData().get("rateRangeLt")==null?null:Integer.valueOf(req.getData().get("rateRangeLt")));
		request.setRateStatus(req.getData().get("rateStatus")==null?null:Integer.valueOf(req.getData().get("rateStatus")));
		request.setSystolicRangeGt(req.getData().get("systolicRangeGt")==null?null:Integer.valueOf(req.getData().get("systolicRangeGt")));
		request.setSystolicRangeLt(req.getData().get("systolicRangeLt")==null?null:Integer.valueOf(req.getData().get("systolicRangeLt")));
		Response<Object> bizRes=http(serverUrl,urlPath,ctx,request,Object.class);
		if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}

}
