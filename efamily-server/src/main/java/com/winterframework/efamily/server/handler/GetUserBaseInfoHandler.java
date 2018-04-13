package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetUserBaseInfoRequest;
import com.winterframework.efamily.dto.GetUserBaseInfoResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取用户基本信息handler
 * @author floy
 *
 */
@Service("getUserBaseInfoHandler")
public class GetUserBaseInfoHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.get_user_base_info")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		GetUserBaseInfoRequest  bizReqList =  new GetUserBaseInfoRequest();
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		
		Response<GetUserBaseInfoResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetUserBaseInfoResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("userBaseInfo", bizRes.getData().getUserBaseInfo());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
		
		
	}
}
