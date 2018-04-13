package com.winterframework.efamily.server.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.PropertyUtil;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.notification.INotificationService;
import com.winterframework.efamily.server.protocol.Command;

@Service("noticeServiceImpl")
public class NoticeServiceImpl implements INoticeService{
	private static final Logger log= LoggerFactory.getLogger(NoticeServiceImpl.class);
    
	@Resource(name="serviceHandler")
	private IServiceHandler serviceHandler;
	@Resource(name="propertyUtil")
	private PropertyUtil propertyUtil;
	@Resource(name="notificationServiceImpl")
	private INotificationService notificationService;
	
	@Override
	public void notify(UserNotice userNotice) throws ServerException{
		if(null!=userNotice){
			NotyTarget t=new NotyTarget();
			t.setUserId(userNotice.getUserId());
			t.setDeviceId(userNotice.getDeviceId());
			
			//Map<String,String> paramMap=userNotice.getParamMap();
			Map<String,String> data=new HashMap<String,String>();
			data.put("notyType", NotyMessage.Type.NOTICE.getCode()+"");
			data.put("noticeType", userNotice.getNoticeType().getValue()+"");
			data.putAll(userNotice.getParamMap());
			
			/*String content=propertyUtil.getProperty(userNotice.getNoticeType().getValue()+"");
			data.put("content", replaceParam(content,paramMap));*/
			
			NotyMessage message=new NotyMessage();
			message.setId(null);
			message.setType(NotyMessage.Type.NOTICE);
			message.setCommand(Command.NOTICE.getCode());
			message.setData(data);
			Notification notification=new Notification();
			notification.setTarget(t);
			notification.setMessage(message);
			 
			notificationService.notify(notification);
		}
	} 
  
    private String replaceParam(String template, Map<String, String> paramMap) {
    	if(null==template) return "";
    	if(null==paramMap) return "";
		try {
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				String key = "{$" + entry.getKey() + "}";
				template = template.replace(key, entry.getValue());
			}
		} catch (Exception e) {
			log.error("replace template param error", e);
		}
		return template;
	}    
}
