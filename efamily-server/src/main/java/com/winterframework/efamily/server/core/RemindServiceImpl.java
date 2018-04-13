package com.winterframework.efamily.server.core;

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
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.RemindStruc;
import com.winterframework.efamily.dto.RemindTaskStruc;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.notification.INotificationService;
import com.winterframework.efamily.server.protocol.Command;

@Service("remindServiceImpl")
public class RemindServiceImpl implements IRemindService {
	private static final Logger log = LoggerFactory.getLogger(RemindServiceImpl.class);

	@Resource(name="notificationServiceImpl")
	private INotificationService notificationService;
	
	@Override
	public void send(List<String> tokenKeyList, RemindStruc chatRecord) throws ServerException{
		if (null != tokenKeyList) {
			for (String tokenKey : tokenKeyList) {
				String tokenKeyReal=new String(Base64Util.getBytesFromBASE64(tokenKey));
            	String[] userInfos=tokenKeyReal.split(Separator.escape+Separator.vertical);
            	Long userId=(1==userInfos.length && null!=userInfos[0])?Long.valueOf(userInfos[0]):null;
            	Long deviceId=(2==userInfos.length && null!=userInfos[1])?Long.valueOf(userInfos[1]):null;
            	
				NotyTarget t=new NotyTarget();
				t.setUserId(userId);
				t.setDeviceId(deviceId);
				 
				  
				RemindTaskStruc struc = new RemindTaskStruc();
				struc.setRemindId(chatRecord.getRemindId());
				struc.setRemindName(chatRecord.getRemindName());
				struc.setUserId(chatRecord.getDeliverUserId());
				struc.setUserName(chatRecord.getDeliverUserName());
				Map<String,String> data=new HashMap<String,String>();
				data.put("notyType", NotyMessage.Type.REMIND.getCode()+"");
				data.put("remind", JsonUtils.toJson(struc));
				
				NotyMessage message=new NotyMessage();
				message.setId(chatRecord.getRemindId());
				message.setType(NotyMessage.Type.REMIND);
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
