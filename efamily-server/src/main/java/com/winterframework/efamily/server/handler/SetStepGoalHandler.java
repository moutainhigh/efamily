/**   
* @Title: SetStepGoalHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-23 下午3:31:11 
* @version V1.0   
*/
package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.SetStepGoalRequest;
import com.winterframework.efamily.dto.SetStepGoalResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: SetStepGoalHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-23 下午3:31:11 
*  
*/
@Service("setStepGoalHandler")
public class SetStepGoalHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.set_step_goal")
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
		SetStepGoalRequest  bizReqList =  new SetStepGoalRequest();
		bizReqList.setTargetStep(Long.valueOf(request.getData().get("targetStep")));
		bizReqList.setType(request.getData().get("type"));
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		Response<SetStepGoalResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,SetStepGoalResponse.class);
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
