package com.winterframework.efamily.server.handler;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.TransferFamilyHostRequest;
import com.winterframework.efamily.dto.TransferFamilyHostResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;


	@Service("transferFamilyHostHandler")
	public class TransferFamilyHostHandler extends AbstractHandler {
		@PropertyConfig("server.url.app")
		private String serverUrl;
		
		@PropertyConfig("app.server.transfer_family_host")
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
			TransferFamilyHostRequest  bizReqList =  new TransferFamilyHostRequest();
			bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
			bizReqList.setFamilyId(Long.valueOf(request.getData().get("familyId")));
			Response<TransferFamilyHostResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,TransferFamilyHostRequest.class);
			FmlResponse res=new FmlResponse(request);
	        if(null!=bizRes){			
				res.setStatus(bizRes.getStatus().getCode()); 
			}else{
				res.setStatus(StatusCode.UNKNOW.getValue());
			}
			return res;
		}

	}
