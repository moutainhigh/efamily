/**   
* @Title: UpdateUserMarkHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-2 下午5:48:59 
* @version V1.0   
*/
package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.UpdateUserMarkRequest;
import com.winterframework.efamily.dto.UpdateUserMarkResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: UpdateUserMarkHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-2 下午5:48:59 
*  
*/
@Service("updateUserMarkHandler")
public class UpdateUserMarkHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.update_user_mark")
	private String urlPath;
	

	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param req
	* @return 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(Context, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		UpdateUserMarkRequest  bizReqList =  new UpdateUserMarkRequest();
		bizReqList.setRemarkName(request.getData().get("remarkName"));
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		
		Response<UpdateUserMarkResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,UpdateUserMarkResponse.class);
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
