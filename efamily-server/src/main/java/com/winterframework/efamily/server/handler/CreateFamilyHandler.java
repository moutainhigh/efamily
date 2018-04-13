package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.CreateFamilyRequest;
import com.winterframework.efamily.dto.CreateFamilyResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 创建家庭
 * @author david
 *
 */
@Service("createFamilyHandler")
public class CreateFamilyHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.create_family")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		CreateFamilyRequest  bizReqList =  new CreateFamilyRequest();
		bizReqList.setFamilyName(request.getData().get("familyName"));
		Response<CreateFamilyResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,CreateFamilyResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
		 	  responseMap.put("familyId", bizRes.getData().getFamilyId()+"");
			  res.setData(responseMap);
			}
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}