package com.winterframework.efamily.server.handler;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.FamilyUserListRequest;
import com.winterframework.efamily.dto.FamilyUserListResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
/**
 * 获取家庭成员列表handler
 * @author david
 *
 */
@Service("familyUserListHandler")
public class FamilyUserListHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.family_userList")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException  {
		FamilyUserListRequest  bizReqList =  new FamilyUserListRequest();
		FmlResponse res=new FmlResponse(request);
		if(request.getData().get("familyId")!=null && request.getData().get("userType")!=null){
			bizReqList.setFamilyId(Long.valueOf(request.getData().get("familyId")));
			bizReqList.setUserType(Long.valueOf(request.getData().get("userType")));
			Response<FamilyUserListResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,FamilyUserListResponse.class);
	        if(null!=bizRes){			
				res.setStatus(bizRes.getStatus().getCode()); 
				Map<String,String> responseMap = new HashMap<String,String>();
				if(bizRes.getData()!=null){
				   responseMap.put("userList", bizRes.getData().getUserList());
				}
				res.setData(responseMap);
			}else{
				res.setStatus(StatusCode.UNKNOW.getValue());
			}
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		
		return res;
	}
}