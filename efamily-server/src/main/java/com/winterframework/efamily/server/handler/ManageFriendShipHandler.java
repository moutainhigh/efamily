package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.ManageFriendShipRequest;
import com.winterframework.efamily.dto.ManageFriendShipResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
* @ClassName: ManageFriendShipHandler 
* @Description: TODO(管理好友：添加和接受) 
* @author jason 
* @date 2015-6-24 下午1:59:48 
*
 */
@Service("manageFriendShipHandler")
public class ManageFriendShipHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.manage_friend_ship")
	private String urlPath;
	

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		ManageFriendShipRequest  bizReqList =  new ManageFriendShipRequest();
		bizReqList.setCustomerId(request.getData().get("customerId"));
		bizReqList.setIsPhoneNumber(Long.valueOf(request.getData().get("isPhoneNumber")));
		bizReqList.setOprType(Long.valueOf(request.getData().get("oprType")));
		
		Response<ManageFriendShipResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,ManageFriendShipResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("phone", bizRes.getData().getPhone());
				responseMap.put("customerId", bizRes.getData().getCustomerId());
				responseMap.put("oprType", bizRes.getData().getOprType()+"");				
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}