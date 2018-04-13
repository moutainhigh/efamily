package com.winterframework.efamily.notification.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.notification.exception.NotificationException;
import com.winterframework.efamily.notification.service.INotificationService;
 
@Controller("notificationController")
@RequestMapping("/notification")
public class NotificationController {
	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
	@Resource(name = "notificationServiceImpl")
	private INotificationService notificationService;
	
	@RequestMapping("/notify")
	@ResponseBody
	public Object notify(@RequestBody Request<Notification> req) {
		Response<Map<String,String>> res=new Response<Map<String,String>>(req);  
		try {
			notificationService.notify(req.getCtx(),req.getData());
			res.setStatus(new Status(StatusCode.OK.getValue()));
		} catch (NotificationException e) {
			log.error(e.getMessage(),e);
			res.setStatus(new Status(e.getCode()));
		}
		return res;
	}
}
