package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.UpdateWatchOwnerRequest;
import com.winterframework.efamily.dto.UpdateWatchOwnerResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.INoticeService;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.notification.INotificationService;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("updateWatchOwnerHandler")
public class UpdateWatchOwnerHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.update_watch_owner")
	private String urlPath;
	
	@Resource(name="noticeServiceImpl")
	private INoticeService noticeServiceImpl;
	
	@Resource(name = "notificationServiceImpl")
	private INotificationService notificationService;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		UpdateWatchOwnerRequest  bizReqList =  new UpdateWatchOwnerRequest();
		bizReqList.setUserId(request.getData().get("userId")) ;
		bizReqList.setDeviceCode(request.getData().get("deviceCode")) ;
		bizReqList.setUserIdExchange(request.getData().get("userIdExchange")) ;
		bizReqList.setOprType(request.getData().get("oprType")) ;
		bizReqList.setNewUserName(request.getData().get("newUserName")) ;
		bizReqList.setPhoneNumber(request.getData().get("phoneNumber")) ;
		
		Response<UpdateWatchOwnerResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,UpdateWatchOwnerResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			 res.setStatus(bizRes.getStatus().getCode()); 
			 Map<String,String> responseMap = new HashMap<String,String>();
			 res.setData(responseMap);
			
			 //*** 更换设备  ******
			 Map<String, String> data = bizRes.getData().getParamMapResult();
			 Long userIdA = StringUtils.isNotBlank(data.get("initiativeUserId"))?Long.valueOf(data.get("initiativeUserId")):null ;
			 Long deviceIdA = StringUtils.isNotBlank(data.get("initiativeDeviceId"))?Long.valueOf(data.get("initiativeDeviceId")):null ;
			 Long userIdB = StringUtils.isNotBlank(data.get("passiveUserId"))?Long.valueOf(data.get("passiveUserId")):null ;
			 Long deviceIdB = StringUtils.isNotBlank(data.get("passiveDeviceId"))?Long.valueOf(data.get("passiveDeviceId")):null ;
			 //**** 更换channel ******
			 ChannelManager.updateChannelForSwitchDevice(userIdA, deviceIdA, userIdB, deviceIdB);
			 //**** 更换token ******
			 TokenManager.updateTokenForSwitchDevice(userIdA, deviceIdA, userIdB, deviceIdB,data);

		}else{
			 res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
	
	
}