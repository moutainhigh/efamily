package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetUserBarrierListResponse;
import com.winterframework.efamily.dto.GetUserBarrierRequest;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("getUserBarrierListHandler")
public class GetUserBarrierListHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	
	@PropertyConfig("app.server.get_userbarrierlist")
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
		GetUserBarrierRequest  bizReqList =  new GetUserBarrierRequest();
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		
		Response<GetUserBarrierListResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetUserBarrierListResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("fenceList", bizRes.getData().getFenceList());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}

}
