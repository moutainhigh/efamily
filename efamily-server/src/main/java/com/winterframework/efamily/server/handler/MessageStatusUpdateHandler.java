package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.MessageStatusUpdateRequest;
import com.winterframework.efamily.dto.MessageStatusUpdateResponse;
import com.winterframework.efamily.dto.SendChatMessageRequest;
import com.winterframework.efamily.dto.SendChatMessageResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 聊天handler
 * 
 * @author david
 * 
 */
@Service("messageStatusUpdateHandler")
public class MessageStatusUpdateHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.message_status_update")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		MessageStatusUpdateRequest  bizReqList =  new MessageStatusUpdateRequest();

		Long sendUserId = Long.valueOf(request.getData().get("sendUserId"));
		Long chatRoomId = Long.valueOf(request.getData().get("chatRoomId"));
		Long chatType = Long.valueOf(request.getData().get("chatType"));
		Long messageId = Long.valueOf(request.getData().get("messageId"));
		Long status = Long.valueOf(request.getData().get("status"));
		
		bizReqList.setChatRoomId(chatRoomId);
		bizReqList.setChatType(chatType);
		bizReqList.setMessageId(messageId);
		bizReqList.setStatus(status);
		bizReqList.setSendUserId(sendUserId);
		bizReqList.setReceiveUserId(ctx.getUserId());
		
		
		Response<MessageStatusUpdateResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,MessageStatusUpdateResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("messageUpdateCount", bizRes.getData().getMessageUpdateCount()+"");
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
