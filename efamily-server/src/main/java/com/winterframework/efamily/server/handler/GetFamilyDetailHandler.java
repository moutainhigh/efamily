package com.winterframework.efamily.server.handler;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetFamilyDetailRequest;
import com.winterframework.efamily.dto.GetFamilyDetailResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;


@Service("getFamilyDetailHandler")
public class GetFamilyDetailHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.get_family_detail")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		GetFamilyDetailRequest  bizReqList =  new GetFamilyDetailRequest();
		bizReqList.setFamilyId(Long.valueOf(request.getData().get("familyId")));
		
		Response<GetFamilyDetailResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetFamilyDetailResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			  responseMap.put("familyCode", bizRes.getData().getFamilyCode());
			  responseMap.put("familyName", bizRes.getData().getFamilyName());
			  responseMap.put("headImgUrl", bizRes.getData().getHeadImgUrl());
			  responseMap.put("familyHostUserId", bizRes.getData().getFamilyHostUserId()+"");
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}