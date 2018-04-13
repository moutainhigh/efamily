package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetVersionInfoRequest;
import com.winterframework.efamily.dto.GetVersionInfoResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: GetVersionInfoHandler 
* @Description: 获取app版本信息
* @author david 
* @date 2015-6-24 上午10:21:29 
*  
*/
@Service("getVersionInfoHandler")
public class GetVersionInfoHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.get_version_info")
	private String urlPath;

	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		GetVersionInfoRequest  bizReqList =  new GetVersionInfoRequest();		
		byte clientType = request.getClinetType();
		bizReqList.setClientType(clientType);
		Response<GetVersionInfoResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetVersionInfoResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("versionNumber", bizRes.getData().getVersionNumber());
				responseMap.put("versionDescribe", bizRes.getData().getVersionDescribe());
				responseMap.put("updateFlag", bizRes.getData().getUpdateFlag());
				responseMap.put("downloadUrl", bizRes.getData().getDownloadUrl());
			}
			res.setData(responseMap);	
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}