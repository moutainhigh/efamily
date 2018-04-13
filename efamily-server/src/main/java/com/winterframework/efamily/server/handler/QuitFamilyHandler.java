package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.QuitFamilyRequest;
import com.winterframework.efamily.dto.QuitFamilyResponse;
import com.winterframework.efamily.dto.UnbindDeviceInfo;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 退出家庭handler
 * @author floy
 *
 */
@Service("quitFamilyHandler")
public class QuitFamilyHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.quit_family")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		QuitFamilyRequest  bizReqList =  new QuitFamilyRequest();
		bizReqList.setFamilyId(Long.valueOf(request.getData().get("familyId")));
		bizReqList.setIsAttentionOldFamilyDevice(Integer.valueOf(request.getData().get("isAttentionOldFamilyDevice")));
		bizReqList.setUserIdStr(request.getData().get("userIdStr"));
		bizReqList.setManageType(Long.valueOf(request.getData().get("manageType")));
		Response<QuitFamilyResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,QuitFamilyResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			//*********** 解绑手表时 需要清空掉 token *****************************
			if(bizRes.getStatus().getCode() == StatusCode.OK.getValue() && bizRes.getData()!=null && bizRes.getData().getUnbindDevicedInfo() !=null ){
				for(UnbindDeviceInfo unbindDeviceInfo:bizRes.getData().getUnbindDevicedInfo()){
					//log.info("解绑手表，清除TOKEN , CHANNEL ：退出家庭时，最后一个人解绑家庭中所有的设备：unbindDeviceInfo = "+unbindDeviceInfo.toString());
					TokenManager.remove(unbindDeviceInfo.getUserId(), unbindDeviceInfo.getDeviceId());
					ChannelManager.remove(unbindDeviceInfo.getUserId(),unbindDeviceInfo.getDeviceId());					
				}
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
