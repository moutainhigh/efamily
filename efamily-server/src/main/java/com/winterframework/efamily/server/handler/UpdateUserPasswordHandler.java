package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.UpdateUserPasswordRequest;
import com.winterframework.efamily.dto.UpdateUserPasswordResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("updateUserPasswordHandler")
public class UpdateUserPasswordHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.update_user_password")
	private String urlPath;

	/**
	 * Title: doHandle Description:
	 * 
	 * @param ctx
	 * @param req
	 * @return
	 * @see 
	 *      
	 */
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request)
			throws ServerException {
		UpdateUserPasswordRequest bizReqList = new UpdateUserPasswordRequest();
		bizReqList.setPassword(request.getData().get("password"));
		bizReqList.setPhoneNumber(request.getData().get("phoneNumber"));
		bizReqList.setVerifyCode(request.getData().get("verifyCode"));
		bizReqList.setOprType(Integer.valueOf(request.getData().get("oprType")));
		bizReqList.setOldPassword(request.getData().get("oldPassword"));
		
		Response<UpdateUserPasswordResponse> bizRes = http(serverUrl, urlPath,ctx, bizReqList, UpdateUserPasswordResponse.class);
		FmlResponse res = new FmlResponse(request);
		if (null != bizRes) {
			res.setStatus(bizRes.getStatus().getCode());
			Map<String, String> responseMap = new HashMap<String, String>();

			res.setData(responseMap);
		} else {
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}

}