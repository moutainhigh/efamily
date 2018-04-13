package com.winterframework.efamily.server.handler;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.SetUserBarrierRequest;
import com.winterframework.efamily.dto.SetUserBarrierResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("setUserBarrierHandler")
public class SetUserBarrierHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	
	@PropertyConfig("app.server.set_userbarrier")
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
		SetUserBarrierRequest  bizReqList =  new SetUserBarrierRequest();
		if(StringUtils.isNotBlank(request.getData().get("fenceId"))){
		   //*** 兼容之前没有fenceId的版本	
			bizReqList.setFenceId(Long.valueOf(request.getData().get("fenceId")));
		}else{
			bizReqList.setFenceId(-1L);
		}
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		bizReqList.setStatus(Integer.valueOf(request.getData().get("status")));
		bizReqList.setX(request.getData().get("x"));
		bizReqList.setY(request.getData().get("y"));
		bizReqList.setRadius(request.getData().get("radius"));
		bizReqList.setType(StringUtils.isNotBlank(request.getData().get("type"))?Integer.valueOf(request.getData().get("type")):0);
		bizReqList.setRemark(request.getData().get("remark"));
		bizReqList.setOrgTag(0);
		Response<SetUserBarrierResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,SetUserBarrierRequest.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}

}