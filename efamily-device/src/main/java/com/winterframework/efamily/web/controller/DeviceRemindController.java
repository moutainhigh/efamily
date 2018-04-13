package com.winterframework.efamily.web.controller;

import java.util.HashMap;
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
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.DeviceBatteryRecordResponse;
import com.winterframework.efamily.dto.device.DeviceChatSettingRequest;
import com.winterframework.efamily.dto.device.DeviceChatSettingResponse;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.service.IDeviceRemindService;
import com.winterframework.efamily.service.IEjlComRemindService;
import com.winterframework.efamily.utils.NotificationUtil;
 
@Controller("deviceRemindController")
@RequestMapping("/device/remind")
public class DeviceRemindController {
	@Resource(name = "deviceRemindServiceImpl")
	private IDeviceRemindService deviceRemindService;
	
	@Resource(name = "httpClientImpl")
	protected IHttpClient httpClientImpl;	
	@Resource(name = "ejlComRemindServiceImpl")
	private IEjlComRemindService ejlComRemindServiceImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceRemindController.class);
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<DeviceChatSettingRequest> req) throws BizException{
		DeviceChatSettingRequest bizReq=req.getData();
		Response<DeviceChatSettingResponse> res=new Response<DeviceChatSettingResponse>(req);
		try{
		res.setData(deviceRemindService.upload(req.getUserId(),req.getDeviceId(),bizReq));
		res.setStatus(new Status(StatusCode.OK.getValue()));
		}catch(Exception e){
			logger.error("/device/remind/upload error", e);
			res.setStatus(new Status(StatusCode.HTTP.getValue()));
		}
	
		return res;
	}
	
	
	@RequestMapping("/sendStatus")
	@ResponseBody
	public Object sendStatus(@RequestBody Request<List<String>> req) throws BizException{
		List<String> list = req.getData();
		Long remindId = Long.valueOf(list.get(1));
		Response<DeviceBatteryRecordResponse> res=new Response<DeviceBatteryRecordResponse>(req);
		try{
			EjlRemind ejlRemind = ejlComRemindServiceImpl.get(remindId);; 
   			ejlRemind.setSentStatus(1l);
   			Context ctx = new Context();
			ctx.set("userId", ejlRemind.getSendUserId());
   			ejlComRemindServiceImpl.save(ctx, ejlRemind);
   			res.setStatus(new Status(StatusCode.OK.getValue()));
   			try {
   				NotyTarget t=new NotyTarget(ejlRemind.getUserId(),null);    
   				Map<String, String> data = new HashMap<String, String>();
   				data.put("remindId", String.valueOf(ejlRemind.getId()));
   				data.put("remindTime", list.get(0)); 
   				data.put("noticeType", NoticeType.REMIND_SEND.getValue()+"");  
   				NotyMessage message=new NotyMessage();
   				message.setId(null);
   				message.setType(NotyMessage.Type.NOTICE);
   				message.setCommand(EfamilyConstant.PUSH);
   				message.setData(data);
   				Notification notification=new Notification();
   				notification.setTarget(t);
   				notification.setMessage(message); 
   				notificationUtil.notification(notification);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			logger.error("/device/sendstatus/error", e);
			res.setStatus(new Status(StatusCode.HTTP.getValue()));
		}
	
		return res;
	}
}
