package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.MessageNoReadRequest;
import com.winterframework.efamily.dto.MessageNoReadResponse;
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
@Service("messageNoReadHandler")
public class MessageNoReadHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.message_no_read")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		MessageNoReadRequest  bizReqList =  new MessageNoReadRequest();
		Response<MessageNoReadResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,MessageNoReadResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("messageNoCountList", bizRes.getData().getMessageNoCountList());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
