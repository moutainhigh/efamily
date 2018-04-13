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
import com.winterframework.efamily.dto.LoginRequest;
import com.winterframework.efamily.dto.LoginResponse;
import com.winterframework.efamily.dto.UpdateUserLoginRecordRequest;
import com.winterframework.efamily.dto.UpdateUserLoginRecordResponse;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.IServiceHandler;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.notification.INotificationService;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.StrUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

import io.netty.channel.Channel;

/**
 * 登陆handler
 * @author david
 *
 */
@Service("loginHandler")
public class LoginHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 	
	@PropertyConfig("app.server.login")
	private String urlPath;	
	@PropertyConfig("app.server.update_user_login_record")
	private String updateUserLoginRecord;	

	@Resource(name="serviceHandler")
	private IServiceHandler serviceHandler;
	
	@Resource(name="notificationServiceImpl")
	private INotificationService notificationService;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {  
		
		FmlResponse res=new FmlResponse(request);
		LoginRequest  bizReqList =  new LoginRequest();
		bizReqList.setInviteNumber(request.getData().get("inviteNumber"));
		bizReqList.setPhoneNumber(request.getData().get("phoneNumber"));
		bizReqList.setType(StrUtils.stringToLong(request.getData().get("type"),null));
		bizReqList.setVerifyCode(request.getData().get("verifyCode"));
		
		//*** 新增字段
		bizReqList.setOprType(StringUtils.isNotBlank(request.getData().get("oprType"))?Integer.valueOf(request.getData().get("oprType")):2);//默认2 验证码登录方式
		bizReqList.setPassword(request.getData().get("password"));
		bizReqList.setSourceType(request.getData().get("sourceType"));
		bizReqList.setSourceId(request.getData().get("sourceId"));
	    
		if((int)request.getClinetType()==21||(int)request.getClinetType()==22){//康朵用户
			ctx.set("appType", 3);	//将用户归属放入token中
		}else if((int)request.getClinetType()==11||(int)request.getClinetType()==12){//若安用户
			ctx.set("appType", 2);	//将用户归属放入token中
		}else if((int)request.getClinetType()==31||(int)request.getClinetType()==32){//华寿用户
			ctx.set("appType", 5);	//将用户归属放入token中
		}else if((int)request.getClinetType()==41||(int)request.getClinetType()==42){//智慧民生
			ctx.set("appType", 6);	//将用户归属放入token中
		}else if((int)request.getClinetType()==51||(int)request.getClinetType()==52){//奇智科技
			ctx.set("appType", 7);	//将用户归属放入token中
		}else if((int)request.getClinetType()==61||(int)request.getClinetType()==62){//北斗天云
			ctx.set("appType", 8);	//将用户归属放入token中
		}else if((int)request.getClinetType()==71||(int)request.getClinetType()==72){//中斗安利信
			ctx.set("appType", 9);	//将用户归属放入token中
		}else{
			ctx.set("appType", 1);;//默认亿家联
		}
		
		
		Response<LoginResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,LoginResponse.class);
        if(StatusCode.OK.getValue() == bizRes.getStatus().getCode() ){		
        	//*** 推送接口108001重复登录 推送数据：device_info(新登陆设备信息) 
        	Long userId = bizRes.getData().getUserId();
        	if(TokenManager.getToken(userId,null)!=null){
        		log.error("testtesttest--token-before:"+TokenManager.getToken(userId,null));
        		log.info("重复登录： userId = "+ bizRes.getData().getUserId()); 
        		try{
	        		Notification notification=new Notification();
	        		NotyTarget target=new NotyTarget();
	        		target.setUserId(userId);
	        		target.setDeviceId(null);
	        		Map<String,String> data=new HashMap<String,String>();
	        		data.put("noticeType", UserNotice.NoticeType.LOGIN_REPEAT_REMOVE_BEFORE_USER.getValue()+"");
	        		data.put("device_info", "APP");
	        		NotyMessage message=new NotyMessage();
	        		message.setType(NotyMessage.Type.NOTICE);
	        		message.setCommand(Command.NOTICE.getCode());
	        		message.setId(null);
	        		message.setData(data);
	        		notification.setTarget(target);
	        		notification.setMessage(message);
	        		notification.setRealTime(Boolean.TRUE);   
	        		notificationService.notify(notification);

/*	        		UpdateUserLoginRecordRequest  bizReq =  new UpdateUserLoginRecordRequest();
	    			bizReq.setStatus(2);// status 2:被踢掉 
	    			bizReq.setToken(TokenManager.getToken(userId, null));
	    			Response<UpdateUserLoginRecordResponse> biz=http(serverUrl,updateUserLoginRecord,ctx,bizReq,UpdateUserLoginRecordResponse.class);
	    			if(biz!=null){
	    				log.debug("记录用户被踢掉状态成功： userId = "+userId);
	    			}*/
	        		
	        		log.info("记录用户被踢掉： userId = "+userId);
	        		
	    			ChannelManager.remove(userId,null);
	    			ChannelManager.save(userId,null,(Channel)ctx.get("channel"));
	    			TokenManager.remove(TokenManager.getToken(userId, null));
    			} catch (Exception e) {
					log.error("重复登录，踢掉另一个用户时，出现异常。userId = "+userId+" ; oldToken = "+TokenManager.getToken(userId, null), e);
				}
    		}
    		res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			responseMap.put("nickName", bizRes.getData().getNickName());
			responseMap.put("headImgUrl", bizRes.getData().getHeadImgUrl());
			responseMap.put("familyCode", bizRes.getData().getFamilyCode());
			responseMap.put("familyName", bizRes.getData().getFamilyName());
			responseMap.put("signature", bizRes.getData().getSignature());
			responseMap.put("userId", bizRes.getData().getUserId()+"");
			responseMap.put("familyId", bizRes.getData().getFamilyId()+"");
			responseMap.put("familyHostUserId", bizRes.getData().getFamilyHostUserId()+"");
			responseMap.put("sex", bizRes.getData().getSex()+"");
			responseMap.put("userType", bizRes.getData().getUserType()+"");
			responseMap.put("source", bizRes.getData().getSource());
			res.setData(responseMap);
			
			ctx.set("userId", bizRes.getData().getUserId());
			ctx.set("nickName", bizRes.getData().getNickName()==null ? "":bizRes.getData().getNickName());
			ctx.set("userType", bizRes.getData().getUserType()==null ? "":bizRes.getData().getUserType());
			ctx.set("familyId", bizRes.getData().getFamilyId()==null ? "":bizRes.getData().getFamilyId());
			ctx.set("deviceId", "");	//用户的设备ID，手机用户则为空 
		
			String token = getToken(ctx);
			res.setToken(token);
			
			UpdateUserLoginRecordRequest  bizReq =  new UpdateUserLoginRecordRequest();
			bizReq.setStatus(1);// status 1:登录 
			bizReq.setToken(token);
			bizReq.setRemark("ip="+request.getIp()+";clientType="+request.getClinetType()+";");
			Response<UpdateUserLoginRecordResponse> biz=http(serverUrl,updateUserLoginRecord,ctx,bizReq,UpdateUserLoginRecordResponse.class);
			if(biz!=null){
				log.debug("记录用户登录状态成功： userId = "+userId+" ; token = "+token+" ; ");
			}
		}else{
			res.setStatus(bizRes.getStatus().getCode());
		}
        log.info("记录登录返回：  res "+res.toString());
		return res;
	}
	private String getToken(Context ctx){  
		return TokenManager.save(ctx);
	}
	
   
}
