package com.winterframework.efamily.server.handler;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.FamilyUserListResponse;
import com.winterframework.efamily.dto.GetDeviceListRequest;
import com.winterframework.efamily.dto.GetDeviceListResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
	/**
	 * 获取设备列表handler
	 * @author jason
	 *
	 */
	@Service("getDeviceListHandler")
	public class GetDeviceListHandler extends AbstractHandler{
		@PropertyConfig("server.url.app")
		private String serverUrl; 
		@PropertyConfig("app.server.device_list")
		private String urlPath;
		
		
		@Override
		protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException  {
			GetDeviceListRequest  bizReqList =  new GetDeviceListRequest();
			FmlResponse res=new FmlResponse(request);

			Response<GetDeviceListResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetDeviceListResponse.class);
	        if(null!=bizRes){			
				res.setStatus(bizRes.getStatus().getCode()); 
				Map<String,String> responseMap = new HashMap<String,String>();
				if(bizRes.getData()!=null){
				   responseMap.put("deviceList", bizRes.getData().getDeviceList());
				}
				res.setData(responseMap);
			}else{
				res.setStatus(StatusCode.UNKNOW.getValue());
			}
			
			return res;
		}
	}
