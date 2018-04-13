package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetChatGroupDetailsRequest;
import com.winterframework.efamily.dto.GetChatGroupDetailsResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
* @ClassName: GetChatGroupDetailsHandler 
* @Description: TODO(获取群组信息) 
* @author jason 
* @date 2015-6-24 上午11:16:22 
*
 */
@Service("getChatGroupDetailsHandler")
public class GetChatGroupDetailsHandler  extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.get_chat_group_details")
	private String urlPath;
	//***聊天类型
	public static final String CHAT_TYPE_ROOM = "3" ;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		GetChatGroupDetailsRequest  bizReqList =  new GetChatGroupDetailsRequest();
		bizReqList.setChatGroupId(Long.valueOf(request.getData().get("chatGroupId")));
		
		//***兼容之前不传chatType的版本(不传默认为聊天群组)
		String chatType = request.getData().get("chatType")==null?CHAT_TYPE_ROOM:request.getData().get("chatType");
		bizReqList.setChatType(Long.valueOf(chatType));
		
		Response<GetChatGroupDetailsResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetChatGroupDetailsResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("memberInfos", bizRes.getData().getMemberInfos());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}