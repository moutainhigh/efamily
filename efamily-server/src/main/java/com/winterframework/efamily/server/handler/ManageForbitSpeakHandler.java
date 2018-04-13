package com.winterframework.efamily.server.handler;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.ManageForbitSpeakRequest;
import com.winterframework.efamily.dto.ManageForbitSpeakResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;


	@Service("manageForbitSpeakHandler")
	public class ManageForbitSpeakHandler extends AbstractHandler{
		@PropertyConfig("server.url.app")
		private String serverUrl;
		@PropertyConfig("app.server.manage_forbit_speak")
		private String urlPath;
		

		@Override
		protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
			ManageForbitSpeakRequest  bizReqList =  new ManageForbitSpeakRequest();
			bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
			bizReqList.setChatType(Long.valueOf(request.getData().get("chatType")));
			bizReqList.setChatRoomId(Long.valueOf(request.getData().get("chatRoomId")));
			bizReqList.setOprType(Integer.valueOf(request.getData().get("oprType")));
			
			Response<ManageForbitSpeakResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,ManageForbitSpeakResponse.class);
			FmlResponse res=new FmlResponse(request);
	        if(null!=bizRes){			
				res.setStatus(bizRes.getStatus().getCode()); 
			}else{
				res.setStatus(StatusCode.UNKNOW.getValue());
			}
			return res;
		}
	}
