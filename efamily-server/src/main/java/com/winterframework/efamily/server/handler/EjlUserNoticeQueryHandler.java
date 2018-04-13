package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetRemindByIdResponse;
import com.winterframework.efamily.dto.UserNoticeStrucRequest;
import com.winterframework.efamily.dto.UserNoticeStrucResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ejlUserNoticeQueryHandler")
public class EjlUserNoticeQueryHandler extends AbstractHandler{

	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.getEjlUserNotice")
	private String urlPath;
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req)
			throws ServerException {
		FmlResponse res=new FmlResponse(req);
		UserNoticeStrucRequest request = new UserNoticeStrucRequest();
		request.setDeviceUserId(Long.valueOf(req.getData().get("deviceUserId")));
		Response<UserNoticeStrucResponse> bizRes=http(serverUrl,urlPath,ctx,request,UserNoticeStrucResponse.class);
		ObjectMapper mapper = new ObjectMapper();
		if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				   try {
					responseMap.put("userNotice", mapper.writeValueAsString(bizRes.getData()));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					res.setStatus(StatusCode.UNKNOW.getValue());
				}
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}

}
