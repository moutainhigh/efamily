package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.MessageLastRequest;
import com.winterframework.efamily.dto.MessageLastResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 聊天记录 获取接口 
 * 
 * @author jason
 * 
 */
@Service("messageLastHandler")
public class MessageLastHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.message_last")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		MessageLastRequest  bizReqList =  new MessageLastRequest();

		Long sendUserId = Long.valueOf(request.getData().get("sendUserId"));
		Long chatRoomId = Long.valueOf(request.getData().get("chatRoomId"));
		Long chatType = Long.valueOf(request.getData().get("chatType"));
		Long lastReadedMessageId = Long.valueOf(request.getData().get("lastReadedMessageId"));
		
		bizReqList.setChatRoomId(chatRoomId);
		bizReqList.setChatType(chatType);
		bizReqList.setSendUserId(sendUserId);
		bizReqList.setReceiveUserId(ctx.getUserId());
		bizReqList.setLastReadedMessageId(lastReadedMessageId);
		
		
		Response<MessageLastResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,MessageLastResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("messageLastId", bizRes.getData().getMessageLastId());
				responseMap.put("noReadMessageSize", bizRes.getData().getNoReadMessageSize()+"");
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
