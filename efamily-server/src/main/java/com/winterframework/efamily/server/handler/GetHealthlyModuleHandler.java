package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetRemindByIdResponse;
import com.winterframework.efamily.dto.UserNoticeStrucRequest;
import com.winterframework.efamily.dto.UserNoticeStrucResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("getHealthlyModuleHandler")
public class GetHealthlyModuleHandler extends AbstractHandler{

	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.getHealthyModule")
	private String urlPath;
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req)
			throws ServerException {
		FmlResponse res=new FmlResponse(req);
		Response<Map> bizRes=http(serverUrl,urlPath,ctx,ctx.getDeviceId(),Map.class);
		ObjectMapper mapper = new ObjectMapper();
		if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				   try {
					responseMap.put("module", mapper.writeValueAsString(bizRes.getData()));
				} catch (JsonProcessingException e) {
					res.setStatus(StatusCode.UNKNOW.getValue());
				}
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}

}
