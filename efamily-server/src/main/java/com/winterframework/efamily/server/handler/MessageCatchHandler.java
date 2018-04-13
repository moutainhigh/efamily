package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.MessageCatchRequest;
import com.winterframework.efamily.dto.MessageCatchResponse;
import com.winterframework.efamily.dto.SendChatMessageRequest;
import com.winterframework.efamily.dto.SendChatMessageResponse;
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
@Service("messageCatchHandler")
public class MessageCatchHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.message_catch")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		MessageCatchRequest  bizReqList =  new MessageCatchRequest();

		Long sendUserId = Long.valueOf(request.getData().get("sendUserId"));
		Long chatRoomId = Long.valueOf(request.getData().get("chatRoomId"));
		Long chatType = Long.valueOf(request.getData().get("chatType"));
		Long page = Long.valueOf(request.getData().get("page"));
		Long pageSize = Long.valueOf(request.getData().get("pageSize"));
		
		bizReqList.setChatRoomId(chatRoomId);
		bizReqList.setChatType(chatType);
		bizReqList.setPage(page);
		bizReqList.setPageSize(pageSize);
		bizReqList.setSendUserId(sendUserId);
		bizReqList.setReceiveUserId(ctx.getUserId());
		
		
		Response<MessageCatchResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,MessageCatchResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("messageList", bizRes.getData().getMessageList());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
