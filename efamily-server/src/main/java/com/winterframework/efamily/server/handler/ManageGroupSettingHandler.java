package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.ManageGroupSettingRequest;
import com.winterframework.efamily.dto.ManageGroupSettingResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;


@Service("manageGroupSettingHandler")
public class ManageGroupSettingHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.manage_group_setting")
	private String urlPath;
	//***聊天类型
	public static final String CHAT_TYPE_ROOM = "3" ;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		ManageGroupSettingRequest  bizReqList =  new ManageGroupSettingRequest();
		bizReqList.setChatGroupId(Long.valueOf(request.getData().get("chatGroupId")));
		bizReqList.setParameterContext(request.getData().get("parameterContext"));
		bizReqList.setParameterIndex(request.getData().get("parameterIndex"));
		bizReqList.setOprType(Long.valueOf(request.getData().get("oprType")));
		//*** 版本兼容：chatType默认为2群组  (因为之前单人聊天不需要设置 置顶 消息通知等信息)
		String chatType =  request.getData().get("chatType")==null?CHAT_TYPE_ROOM:request.getData().get("chatType");
		bizReqList.setChatType(Long.valueOf(chatType));
		Response<ManageGroupSettingResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,ManageGroupSettingResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("parameterIndex", bizRes.getData().getParameterIndex());
				responseMap.put("parameterContext", bizRes.getData().getParameterContext());
				responseMap.put("chatGroupId", bizRes.getData().getChatGroupId()+"");
				responseMap.put("oprType", request.getData().get("oprType")+"");
				responseMap.put("chatType", chatType);
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}