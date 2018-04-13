/**   
* @Title: FormulaUtils.java 
* @Package com.winterframework.efamily.server.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-7 上午9:49:41 
* @version V1.0   
*/
package com.winterframework.efamily.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Component("notificationUtil")
public class NotificationUtil {
	private static final Logger log = LoggerFactory.getLogger(NotificationUtil.class);
	@PropertyConfig(value="server.url")
	private String serverUrl;
	@PropertyConfig(value="server.url.action.push")
	private String serverUrlPush;
	@PropertyConfig(value="server.url.action.push.bind")
	private String serverUrlPushBind;
	@Resource(name="httpClientImpl")
	private IHttpClient httpClient;
	
	public void notification(Notification notification) throws BizException{ 
		try{
			httpClient.invokeHttp(serverUrl+serverUrlPush, new Context(), notification, Notification.class);
		}catch(Exception e){
			log.error("notification failed."+e);
			throw new BizException(StatusBizCode.PUSH_FAILED.getValue());
		}
	}
	
	public void notification(Long userId,Long deviceId,int command,NotyMessage.Type notyType,Map<String,String> data,boolean isRealTime) throws BizException{ 
    	 try{
	    	 Notification notification = new Notification();
			 if(data==null){
		        data = new HashMap<String,String>();
		     }
	 		 log.info("操作设备，推送命令给设备： userId = "+userId+" ; deviceId = "+deviceId+" ; command = "+command+" ; data = "+data.toString()); 
			 NotyTarget target = new NotyTarget(userId,deviceId);	//推送目标 
			 
			 NotyMessage message = new NotyMessage();
			 message.setCommand(command);
			 message.setType(notyType);
			 message.setData(data);

			 notification.setTarget(target);
			 notification.setMessage(message);
			 notification.setRealTime(isRealTime); //是否实时推送		 
			 notification(notification);
    	 }catch(Exception e){
    		 log.error("推送异常：",e);
    		 throw new BizException(StatusBizCode.PUSH_FAILED.getValue());
    	 }
	}
	
	public void notificationBind(NotificationBind notyBind) throws BizException{ 
   	 try{
   		try{
			httpClient.invokeHttp(serverUrl+serverUrlPushBind, new Context(), notyBind, NotificationBind.class);
		}catch(Exception e){
			log.error("notification failed."+e);
			throw new BizException(StatusBizCode.PUSH_FAILED.getValue());
		}
   	 }catch(Exception e){
   		 log.error("推送异常：",e);
   		 throw new BizException(StatusBizCode.PUSH_FAILED.getValue());
   	 }
	}
	
	
	public void notifyUser(Map<String,String> data,List<EjlUser> userList,Long userId,NoticeType noticeType,Long notNoticeUserId){
    	if(userList == null || userList.size()==0){
			log.info("需要推送的用户为空。 userId = "+userId+" ; noticeType = "+noticeType);
			return;
    	}
   		for(EjlUser userTemp : userList){
   			if(notNoticeUserId!=null && notNoticeUserId.longValue() == userTemp.getId().longValue()){
   				continue;
   			} 
   			try { 
				NotyTarget t=new NotyTarget(userTemp.getId(),null); 
				
				//Map<String,String> paramMap=userNotice.getParamMap();
				Map<String,String> data2=new HashMap<String,String>(); 
				data2.put("noticeType", noticeType.getValue()+"");
				data2.putAll(data);
				
				/*String content=propertyUtil.getProperty(userNotice.getNoticeType().getValue()+"");
				data.put("content", replaceParam(content,paramMap));*/
				
				NotyMessage message=new NotyMessage();
				message.setId(null);
				message.setType(NotyMessage.Type.NOTICE);
				message.setCommand(EfamilyConstant.PUSH);
				message.setData(data2);
				Notification notification=new Notification();
				notification.setTarget(t);
				notification.setMessage(message);
				 
				this.notification(notification);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("推送[notifyUser]时出现异常：  userId = "+userTemp.getId());
			}
   		}
     }
}
