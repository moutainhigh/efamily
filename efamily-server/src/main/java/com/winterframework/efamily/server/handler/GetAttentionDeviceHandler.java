package com.winterframework.efamily.server.handler;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.FamilyUserListResponse;
import com.winterframework.efamily.dto.GetAttentionDeviceRequest;
import com.winterframework.efamily.dto.GetAttentionDeviceResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
	/**
 * @author jason
 *
 */
@Service("getAttentionDeviceHandler")
public class GetAttentionDeviceHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.get_attention_device")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException  {
		GetAttentionDeviceRequest  bizReqList =  new GetAttentionDeviceRequest();
		FmlResponse res=new FmlResponse(request);

		Response<GetAttentionDeviceResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetAttentionDeviceResponse.class);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("AttentionDevice", bizRes.getData().getDeviceList());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		
		return res;
	}
}

