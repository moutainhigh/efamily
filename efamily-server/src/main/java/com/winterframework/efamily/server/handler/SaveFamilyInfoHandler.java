package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.SaveFamilyInfoRequest;
import com.winterframework.efamily.dto.SaveFamilyInfoResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("saveFamilyInfoHandler")
public class SaveFamilyInfoHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.save_family_info")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		SaveFamilyInfoRequest  bizReqList =  new SaveFamilyInfoRequest();
		bizReqList.setFamilyId( StringUtils.isBlank(request.getData().get("familyId"))?null:Long.valueOf(request.getData().get("familyId")));
		bizReqList.setFamilyName(request.getData().get("familyName"));
		bizReqList.setHeadImgUrl(request.getData().get("headImgUrl"));
		
		Response<SaveFamilyInfoResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,SaveFamilyInfoResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("familyId", bizRes.getData().getFamilyId()+"");
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}