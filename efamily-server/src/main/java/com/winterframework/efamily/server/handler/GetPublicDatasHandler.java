package com.winterframework.efamily.server.handler;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetPublicDatasRequest;
import com.winterframework.efamily.dto.GetPublicDatasResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: GetPublicDatasHandler 
* @Description: 获取公共数据配置接口
* @author david 
* @date 2015-6-24 上午10:21:29 
*  
*/
@Service("getPublicDatasHandler")
public class GetPublicDatasHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.get_public_datas")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		GetPublicDatasRequest  bizReqList =  new GetPublicDatasRequest();
		byte clientType = request.getClinetType();
		bizReqList.setClientType(clientType);
		bizReqList.setAppVersion(request.getData().get("appVersion")) ;
		Response<GetPublicDatasResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetPublicDatasResponse.class);
		
		FmlResponse res=new FmlResponse(request);
		if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("versionNumber", bizRes.getData().getVersionNumber());
				responseMap.put("versionDescribe", bizRes.getData().getVersionDescribe());
				responseMap.put("updateFlag", bizRes.getData().getUpdateFlag());
				responseMap.put("downloadUrl", bizRes.getData().getDownloadUrl());
				responseMap.put("logoUrl", bizRes.getData().getLogoUrl());
				
				responseMap.put("weChat", bizRes.getData().getWeChat());
				responseMap.put("weiboName", bizRes.getData().getWeiboName());
				responseMap.put("weiboUrl", bizRes.getData().getWeiboUrl());
				responseMap.put("phoneNumber", bizRes.getData().getPhoneNumber());
				responseMap.put("verifyCodeLength", bizRes.getData().getVerifyCodeLength());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		
		return res;
		
		
	}
}