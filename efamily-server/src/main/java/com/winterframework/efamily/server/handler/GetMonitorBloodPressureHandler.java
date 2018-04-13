package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetMonitorBloodDataResponse;
import com.winterframework.efamily.dto.GetMonitorDataRequest;
import com.winterframework.efamily.dto.GetMonitorDataResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.StrUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Service("getMonitorBloodPressureHandler")
public class GetMonitorBloodPressureHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.getMonitorBloodPressureData")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		GetMonitorDataRequest  bizReqList =  new GetMonitorDataRequest();
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId"))) ;
		bizReqList.setDeviceId(StrUtils.stringToLong(request.getData().get("deviceId"),null)) ;
		bizReqList.setStartDateTime(request.getData().get("startDateTime")==null?null:Long.valueOf(request.getData().get("startDateTime")));
		bizReqList.setEndDateTime(request.getData().get("endDateTime")==null?null:Long.valueOf(request.getData().get("endDateTime")));
		Response<GetMonitorBloodDataResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetMonitorBloodDataResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				ObjectMapper mapper = new ObjectMapper();
				try {
					responseMap.put("bloodPressure", mapper.writeValueAsString(bizRes.getData()));
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
