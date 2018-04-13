package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetUserBarrierRequest;
import com.winterframework.efamily.dto.GetUserBarrierResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

	@Service("getUserBarrierHandler")
	public class GetUserBarrierHandler extends AbstractHandler {
		@PropertyConfig("server.url.app")
		private String serverUrl;
		
		@PropertyConfig("app.server.get_userbarrier")
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
			
			Response<GetUserBarrierResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetUserBarrierResponse.class);
			FmlResponse res=new FmlResponse(request);
	        if(null!=bizRes){			
				res.setStatus(bizRes.getStatus().getCode()); 
				Map<String,String> responseMap = new HashMap<String,String>();
				if(bizRes.getData()!=null){
					responseMap.put("x", bizRes.getData().getX());
					responseMap.put("y", bizRes.getData().getY());
					responseMap.put("radius", bizRes.getData().getRadius()+"");
					responseMap.put("status", bizRes.getData().getStatus()+"");
					responseMap.put("userId", bizRes.getData().getUserId()+"");
					responseMap.put("type", bizRes.getData().getType()+"");
					responseMap.put("remark", bizRes.getData().getRemark()+"");
				}
				res.setData(responseMap);
			}else{
				res.setStatus(StatusCode.UNKNOW.getValue());
			}
			return res;
		}

	}