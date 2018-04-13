package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.device.DeviceBindConfirmRequest;
import com.winterframework.efamily.dto.device.DeviceBindConfirmResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 设备绑定确认
 * 
 * @ClassName
 * @Description
 * @author ibm 2015年3月17日
 */
@Service("deviceBindHandler")
public class DeviceBindHandler extends AbstractHandler {
	@PropertyConfig("server.url.device")
	private String serverUrl;
	@PropertyConfig("device.bind")
	private String urlPath;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req)
			throws ServerException {
		Long toId = Long.valueOf(req.getValue("toId"));
		Integer status = Integer.valueOf(req.getValue("status"));

		DeviceBindConfirmRequest bizReq = new DeviceBindConfirmRequest();
		bizReq.setToId(toId);
		bizReq.setStatus(status);
		FmlResponse res = new FmlResponse(req);
		Response<DeviceBindConfirmResponse> bizRes = http(serverUrl,urlPath, ctx, bizReq,
				DeviceBindConfirmResponse.class);
		/*
		 * Long userId=bizRes.getData().getId();
		 * res.setValue("userId",userId+"");
		 */
		res = getFmlResponse(res, bizRes);
		return res;
	}

}