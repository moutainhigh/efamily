package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.SearchFamilyRequest;
import com.winterframework.efamily.dto.SearchFamilyResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取身份列表handler
 * @author david
 *
 */
@Service("searchFamilyHandler")
public class SearchFamilyHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.search_family")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		SearchFamilyRequest  bizReqList =  new SearchFamilyRequest();
		bizReqList.setKey(request.getData().get("key"));
		
		Response<SearchFamilyResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,SearchFamilyResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("resultList", bizRes.getData().getResultList());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
