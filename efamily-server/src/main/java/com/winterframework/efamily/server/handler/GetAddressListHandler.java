/**   
* @Title: GetAddressListHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-14 下午4:45:07 
* @version V1.0   
*/
package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetAddressListRequest;
import com.winterframework.efamily.dto.GetAddressListResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: GetAddressListHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-14 下午4:45:07 
*  
*/
@Service("getAddressListHandler")
public class GetAddressListHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.get_address_list")
	private String urlPath;
	
	/**
	* Title: handle
	* Description:
	* @param request
	* @return 
	* @see com.winterframework.efamily.server.core.IHandler#handle(com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		GetAddressListRequest  bizReqList =  new GetAddressListRequest();
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		
		Response<GetAddressListResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetAddressListResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("deviceAddressList", bizRes.getData().getDeviceAddressList());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
