/**   
* @Title: GetRemindByIdHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-10 下午2:06:55 
* @version V1.0   
*/
package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetUserRemindListRequest;
import com.winterframework.efamily.dto.GetUserRemindListResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: GetRemindByIdHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-10 下午2:06:55 
*  
*/
@Service("getUserRemindListHandler")
public class GetUserRemindListHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.get_user_remind_list")
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
		GetUserRemindListRequest  bizReqList =  new GetUserRemindListRequest();
		Response<GetUserRemindListResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetUserRemindListResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("remindList", bizRes.getData().getRemindList());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
