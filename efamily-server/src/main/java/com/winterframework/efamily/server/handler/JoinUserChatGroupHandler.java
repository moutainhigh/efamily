package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.JoinUserChatGroupRequest;
import com.winterframework.efamily.dto.JoinUserChatGroupResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;


@Service("joinUserChatGroupHandler")
public class JoinUserChatGroupHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.join_user_chat_group")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		JoinUserChatGroupRequest  bizReqList =  new JoinUserChatGroupRequest();
		bizReqList.setChatRoomId(Long.valueOf(request.getData().get("chatRoomId")));
		bizReqList.setUserIds(request.getData().get("userIds"));
		
		Response<JoinUserChatGroupResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,JoinUserChatGroupResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}