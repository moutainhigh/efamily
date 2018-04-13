package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.CreateChatGroupRequest;
import com.winterframework.efamily.dto.CreateChatGroupResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
* 
* @ClassName: CreateChatGroupHandler 
* @Description: TODO(创建聊天群组，并且往聊天群组中加入用户) 
* @author jason 
* @date 2015-6-24 上午9:47:47 
*
 */
@Service("createChatGroupHandler")
public class CreateChatGroupHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	
	@PropertyConfig("app.server.create_chat_group")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		CreateChatGroupRequest bizReqList= new CreateChatGroupRequest();
		bizReqList.setMemberIds(request.getData().get("memberIds"));
		
		Response<CreateChatGroupResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,CreateChatGroupResponse.class);	
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			  responseMap.put("chatGroupId", bizRes.getData().getChatGroupId()+"");
			  responseMap.put("chatRoomName", bizRes.getData().getChatRoomName());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}