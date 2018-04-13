package com.winterframework.efamily.server.core;
 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.dto.Message;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.notification.INotificationService;
import com.winterframework.efamily.server.protocol.Command;
@Service("messageServiceImpl")
public class MessageServiceImpl implements IMessageService{
	private static final Logger log= LoggerFactory.getLogger(MessageServiceImpl.class);
	@Resource(name="notificationServiceImpl")
	private INotificationService notificationService;
	@Override
	public void send(List<String> tokenKeyList,Message chatRecord) throws ServerException{
		if(null!=tokenKeyList){  
			for(String tokenKey:tokenKeyList){
				String tokenKeyReal=new String(Base64Util.getBytesFromBASE64(tokenKey));
            	String[] userInfos=tokenKeyReal.split(Separator.escape+Separator.vertical);
            	Long userId=(1==userInfos.length && null!=userInfos[0])?Long.valueOf(userInfos[0]):null;
            	Long deviceId=(2==userInfos.length && null!=userInfos[1])?Long.valueOf(userInfos[1]):null;
            	
				NotyTarget t=new NotyTarget();
				t.setUserId(userId);
				t.setDeviceId(deviceId);
				 
				Map<String,String> data=new HashMap<String,String>();
				data.put("notyType", NotyMessage.Type.MSG.getCode()+"");
				data.put("sendUserType", "2");
				data.put("content", chatRecord.getContent());
				data.put("contentType", chatRecord.getContentType()+"");
				data.put("contentTime", chatRecord.getContentTime()+"");
				data.put("sendTime", new Date().getTime()+"");
				data.put("chatGroupId", chatRecord.getChatRoomId()+"");
				data.put("chatType", chatRecord.getChatType()+"");
				data.put("sendUserId", chatRecord.getSendUserId()+"");
				data.put("messageId", chatRecord.getId()+"");
				data.put("senderName", chatRecord.getSenderName());
				NotyMessage message=new NotyMessage();
				message.setId(chatRecord.getId());
				message.setType(NotyMessage.Type.MSG);
				message.setCommand(Command.NOTICE.getCode());
				message.setData(data);
				Notification notification=new Notification();
				notification.setTarget(t);
				notification.setMessage(message);
				 
				notificationService.notify(notification);
			}
		}
	} 
  
    
}
