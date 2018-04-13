package com.winterframework.efamily.server.handler.device;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetUserBaseInfoRequest;
import com.winterframework.efamily.dto.device.DeviceUserBaseInfo;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("deviceUserBaseInfoHandler")
public class DeviceUserBaseInfoHandler extends AbstractHandler {
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.user_info")
	private String urlPath;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request)
			throws ServerException {
		GetUserBaseInfoRequest bizReqList = new GetUserBaseInfoRequest();
		bizReqList.setUserId(Long.valueOf(request.getValue("userId")));
		FmlResponse res = new FmlResponse(request);
		try {
			Response<DeviceUserBaseInfo> bizRes = http(serverUrl, urlPath,
					ctx, bizReqList, DeviceUserBaseInfo.class);
			if (null != bizRes) {
				res.setStatus(bizRes.getStatus().getCode());
				res.setValue("nickName", bizRes.getData().getNickName());
				res.setValue("sex", bizRes.getData().getSex());
				res.setValue("headImage", bizRes.getData().getHeadImage());
				res.setValue("phoneNumber", bizRes.getData().getPhoneNumber());
				res.setValue("userId", bizRes.getData().getUserId());
			} else {
				res.setStatus(StatusCode.UNKNOW.getValue());
			}
		}catch (Exception e) {
				res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
