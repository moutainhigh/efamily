/**   
* @Title: SendInviteMessageHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-20 上午10:47:41 
* @version V1.0   
*/
package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.SendInviteMessageRequest;
import com.winterframework.efamily.dto.SendInviteMessageResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: SendInviteMessageHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-20 上午10:47:41 
*  
*/
/**
 * 发送邀请信息handler
 * @author floy
 *
 */
@Service("sendInviteMessageHandler")
public class SendInviteMessageHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.send_invite_message")
	private String urlPath;
	
	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param request
	* @return 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(Context, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		SendInviteMessageRequest  bizReqList =  new SendInviteMessageRequest();
		bizReqList.setFamilyId(Long.valueOf(request.getData().get("familyId")));
		bizReqList.setInvitePhoneNumber(request.getData().get("invitePhoneNumber"));
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		
		Response<SendInviteMessageResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,SendInviteMessageResponse.class);
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
