package com.winterframework.efamily.unit.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.modules.test.BaseTestCase;

public class NotificationPressureTest extends BaseTestCase {
	Logger log = LoggerFactory.getLogger(CreateChatRoomGroupTest.class);

	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	
	@Test
	@Rollback(false)
	public void test() throws Exception { 
		String onUserIdPre="99999";
		String offUserIdPre="66666";
		Long userId;
		Long deviceId=null;
		boolean msg=true;
		boolean oper=true;
		boolean notice=true;
		boolean alarm=true;
		boolean remind=true;
		boolean sett=true;
		boolean isOnline=true;
		int count=100;
		if(oper){
	        for (int i = 0; i < count; i++) {
	        	userId=isOnline?Long.valueOf(onUserIdPre+i):Long.valueOf(offUserIdPre+i);
	        	Map<String,String> data=new HashMap<String,String>(); 
				data.put("paramCode", "paramCode");
				data.put("paramValue", "paramValue");
				
				NotyTarget target=new NotyTarget(userId, deviceId);
				NotyMessage message=new NotyMessage();
				message.setCommand(20203);
				message.setType(NotyMessage.Type.OPER);
				message.setData(data);
				Notification notification=new Notification();
				notification.setTarget(target);
				notification.setMessage(message);
				notification.setRealTime(false);
				notificationUtil.notification(notification);
			}
		}
		/*if(oper){
	        for (int i = 0; i < count; i++) {
	        	userId=isOnline?Long.valueOf(onUserIdPre+i):Long.valueOf(offUserIdPre+i);
	        	Map<String,String> data=new HashMap<String,String>(); 
				data.put("paramCode", "paramCode");
				data.put("paramValue", "paramValue");
				
				NotyTarget target=new NotyTarget(userId, deviceId);
				NotyMessage message=new NotyMessage();
				message.setCommand(20203);
				message.setType(NotyMessage.Type.OPER);
				message.setData(data);
				Notification notification=new Notification();
				notification.setTarget(target);
				notification.setMessage(message);
				notification.setRealTime(false);
				notificationUtil.notification(notification);
			}
		}
		if(notice){
	        for (int i = 0; i < count; i++) {
	        	userId=isOnline?Long.valueOf(onUserIdPre+i):Long.valueOf(offUserIdPre+i);
	        	Map<String,String> data=new HashMap<String,String>(); 
				data.put("paramCode", "paramCode");
				data.put("paramValue", "paramValue");
				
				NotyTarget target=new NotyTarget(userId, deviceId);
				NotyMessage message=new NotyMessage();
				message.setCommand(20203);
				message.setType(NotyMessage.Type.NOTICE);
				message.setData(data);
				Notification notification=new Notification();
				notification.setTarget(target);
				notification.setMessage(message);
				notification.setRealTime(false);
				notificationUtil.notification(notification);
			}
		}
		if(alarm){
	        for (int i = 0; i < count; i++) {
	        	userId=isOnline?Long.valueOf(onUserIdPre+i):Long.valueOf(offUserIdPre+i);
	        	Map<String,String> data=new HashMap<String,String>(); 
				data.put("paramCode", "paramCode");
				data.put("paramValue", "paramValue");
				
				NotyTarget target=new NotyTarget(userId, deviceId);
				NotyMessage message=new NotyMessage();
				message.setCommand(20203);
				message.setType(NotyMessage.Type.ALARM);
				message.setData(data);
				Notification notification=new Notification();
				notification.setTarget(target);
				notification.setMessage(message);
				notification.setRealTime(false);
				notificationUtil.notification(notification);
			}
		}
		if(sett){
	        for (int i = 0; i < count; i++) {
	        	userId=isOnline?Long.valueOf(onUserIdPre+i):Long.valueOf(offUserIdPre+i);
	        	Map<String,String> data=new HashMap<String,String>(); 
				data.put("paramCode", "paramCode");
				data.put("paramValue", "paramValue");
				
				NotyTarget target=new NotyTarget(userId, deviceId);
				NotyMessage message=new NotyMessage();
				message.setCommand(20203);
				message.setType(NotyMessage.Type.SETT);
				message.setData(data);
				Notification notification=new Notification();
				notification.setTarget(target);
				notification.setMessage(message);
				notification.setRealTime(false);
				notificationUtil.notification(notification);
			}
		}
		if(remind){
	        for (int i = 0; i < count; i++) {
	        	userId=isOnline?Long.valueOf(onUserIdPre+i):Long.valueOf(offUserIdPre+i);
	        	Map<String,String> data=new HashMap<String,String>(); 
				data.put("paramCode", "paramCode");
				data.put("paramValue", "paramValue");
				
				NotyTarget target=new NotyTarget(userId, deviceId);
				NotyMessage message=new NotyMessage();
				message.setCommand(20203);
				message.setType(NotyMessage.Type.REMIND);
				message.setData(data);
				Notification notification=new Notification();
				notification.setTarget(target);
				notification.setMessage(message);
				notification.setRealTime(false);
				notificationUtil.notification(notification);
			}
		}*/
	}

}