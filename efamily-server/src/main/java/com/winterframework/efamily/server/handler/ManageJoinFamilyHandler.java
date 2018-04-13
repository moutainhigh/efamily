/**   
* @Title: ManageJoinFamilyHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-11 上午9:53:12 
* @version V1.0   
*/
package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.ManageJoinFamilyRequest;
import com.winterframework.efamily.dto.ManageJoinFamilyResponse;
import com.winterframework.efamily.dto.UnbindDeviceInfo;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: ManageJoinFamilyHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-11 上午9:53:12 
*  
*/
@Service("manageJoinFamilyHandler")
public class ManageJoinFamilyHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.manage_join_family")
	private String urlPath;
	
	
	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param request
	* @return 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(Context, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		ManageJoinFamilyRequest  bizReqList =  new ManageJoinFamilyRequest();
		bizReqList.setApplyUserid(request.getData().get("applyUserid"));
		bizReqList.setFamilyCode(request.getData().get("familyCode"));
		bizReqList.setFamilyId(request.getData().get("familyId"));
		bizReqList.setIsPhoneNumber(Long.valueOf(request.getData().get("isPhoneNumber")));
		bizReqList.setManageType(request.getData().get("manageType"));
		
		Response<ManageJoinFamilyResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,ManageJoinFamilyResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("familyId", bizRes.getData().getFamilyId()+"");
			}
			res.setData(responseMap);
			//*********** 解绑手表时 需要清空掉 token *****************************
			if(bizRes.getStatus().getCode() == StatusCode.OK.getValue() && bizRes.getData()!=null && bizRes.getData().getUnbindDeviceInfo() !=null ){
				for(UnbindDeviceInfo unbindDeviceInfo:bizRes.getData().getUnbindDeviceInfo()){
					log.info("解绑手表，清除TOKEN , CHANNEL ：申请或者接受邀请 加入别人家庭时，如果此人是家庭中最后一个人，则解绑家庭中所有的设备：unbindDeviceInfo = "+unbindDeviceInfo.toString());
					TokenManager.remove(unbindDeviceInfo.getUserId(), unbindDeviceInfo.getDeviceId());
					ChannelManager.remove(unbindDeviceInfo.getUserId(),unbindDeviceInfo.getDeviceId());					
				}
			}
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
	
	
	
	
}
