package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
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
@Service("sendChatMessageHandler")
public class SendChatMessageHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.send_chat_message")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		SendChatMessageRequest  bizReqList =  new SendChatMessageRequest();
		bizReqList.setContentType(request.getData().get("contentType"));
		bizReqList.setContentTime(request.getData().get("contentTime"));
		bizReqList.setReceiveUserId(request.getData().get("receiveUserId"));
		bizReqList.setReceiveUserType(request.getData().get("receiveUserType"));
		bizReqList.setContent(request.getData().get("content"));
		bizReqList.setAppSendTime(request.getData().get("appSendTime"));
		
		Response<SendChatMessageResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,SendChatMessageResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("sendTime", bizRes.getData().getSendTime());
			   responseMap.put("messageId", bizRes.getData().getMessageId()+"");
			   responseMap.put("appSendTime", bizRes.getData().getAppSendTime()+"");
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
