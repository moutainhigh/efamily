package com.winterframework.efamily.server.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.Message;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.dto.RemindStruc;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.IDeviceService;
import com.winterframework.efamily.server.core.IMessageService;
import com.winterframework.efamily.server.core.INoticeService;
import com.winterframework.efamily.server.core.IRemindService;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.notification.INotificationService;
 
@Controller("pushController")
@RequestMapping("/push")
public class PushController {
	private static final Logger log = LoggerFactory.getLogger(PushController.class);
	@Resource(name = "notificationServiceImpl")
	private INotificationService notificationService;
	@Resource(name = "messageServiceImpl")
	private IMessageService messageService; 
	
	@Resource(name = "remindServiceImpl")
	private IRemindService remindService; 
	@Resource(name = "noticeServiceImpl")
	private INoticeService noticeService; 
	@Resource(name = "deviceServiceImpl")
	private IDeviceService deviceService; 
	
	@RequestMapping("/")
	@ResponseBody
	public Object push(@RequestBody Request<Notification> req) {
		Response<Map<String,String>> res=new Response<Map<String,String>>(req);  
		try {
			notificationService.notify(req.getData());
			res.setStatus(new Status(StatusCode.OK.getValue()));
		} catch (ServerException e) {
			log.error(e.getMessage(),e);
			res.setStatus(new Status(e.getCode()));
		}
		return res;
	}
	@RequestMapping("/bind")
	@ResponseBody
	public Object bind(@RequestBody Request<NotificationBind> req) {
		Response<Map<String,String>> res=new Response<Map<String,String>>(req);  
		try {
			notificationService.notifyBind(req.getData());
			res.setStatus(new Status(StatusCode.OK.getValue()));
		} catch (ServerException e) {
			log.error(e.getMessage(),e);
			res.setStatus(new Status(e.getCode()));
		}
		return res;
	}
	@RequestMapping("/message")
	@ResponseBody
	public Object message(@RequestBody Request<Message> req) {
		Message data=req.getData();
		Response<Message> res=new Response<Message>(req);		 
		List<String> tokenKeyList=new ArrayList<String>();
		tokenKeyList.add(TokenManager.getTokenKey(req.getUserId(), req.getDeviceId()));
		try {
			messageService.send(tokenKeyList, data);
		} catch (ServerException e) {
			log.error(e.getMessage(),e);
			res.setStatus(new Status(e.getCode()));
		}
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/remind")
	@ResponseBody
	public Object remind(@RequestBody Request<RemindStruc> req) {
		RemindStruc data=req.getData();		 
		Response<RemindStruc> res=new Response<RemindStruc>(req);
		List<String> tokenKeyList=new ArrayList<String>();
		tokenKeyList.add(TokenManager.getTokenKey(req.getUserId(), req.getDeviceId()));
		try{
			remindService.send(tokenKeyList, data);
		} catch (ServerException e) {
			log.error(e.getMessage(),e);
			res.setStatus(new Status(e.getCode()));
		}
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/notice")
	@ResponseBody
	public Object notice(@RequestBody Request<UserNotice> req) {
		log.info("NNNNNNNNNNNNNNNotice:"+req.getData());
		UserNotice data=req.getData();		 
		Response<UserNotice> res=new Response<UserNotice>(req); 
		try{
			noticeService.notify(data);
		} catch (ServerException e) {
			log.error(e.getMessage(),e);
			res.setStatus(new Status(e.getCode()));
		}
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}

	@RequestMapping("/device")
	@ResponseBody
	public Object device(@RequestBody Request<Map<String,String>> req) {
		Map<String,String> m=req.getData();
		StringBuffer sb=new StringBuffer();
		sb.append("\r\n**********PUSH REQUEST DATA:***********\r\n");
		for(Map.Entry<String,String> e:m.entrySet()){
			sb.append("key=").append(e.getKey());
			sb.append(" ");
			sb.append("value=").append(e.getValue());
			sb.append("\r\n");
		}
		log.info(sb.toString());
		
		Long userId=Long.valueOf(req.getData().remove("deviceUserId"));
		Long deviceId=Long.valueOf(req.getData().remove("deviceId"));
		Context ctx=new Context();
		ctx.set("userId", userId);
		ctx.set("deviceId", deviceId);
		Response<Map<String,String>> res=new Response<Map<String,String>>(req);  
		try {
			NotyTarget target=new NotyTarget(userId, deviceId);
			NotyMessage message=new NotyMessage();
			message.setCommand(Integer.valueOf(req.getData().remove("command")));
			message.setType(NotyMessage.Type.MSG);
			message.setData(req.getData());
			Notification notification=new Notification();
			notification.setTarget(target);
			notification.setMessage(message);
			notification.setRealTime(false);
			notificationService.notify(notification);
			res.setStatus(new Status(StatusCode.OK.getValue()));
		} catch (ServerException e) {
		 	log.error(e.getMessage(),e);
			res.setStatus(new Status(e.getCode()));
		}
		return res;
	}
	@RequestMapping("/getCurrentChannelLinkUserInfo")
	@ResponseBody
	public Object getCurrentChannelLinkUserInfo(@RequestBody Request<Map<String,String>> req) {
			Map<String,String> data=req.getData();
			Response<Map<String,String>> res=new Response<Map<String,String>>(req);  
			try {
				String channelMapUsers = ChannelManager.getChannelMapUsers();
				data.put("channelMapUsers", channelMapUsers);
				res.setData(data);
				res.setCtx(req.getCtx());
				res.setStatus(new Status(StatusCode.OK.getValue()));
			} catch (Exception e) {
			 	log.error(e.getMessage(),e);
				res.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			}
			return res;
   }
}
