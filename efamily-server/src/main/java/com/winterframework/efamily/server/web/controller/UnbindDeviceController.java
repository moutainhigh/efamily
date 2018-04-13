package com.winterframework.efamily.server.web.controller;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;

import com.winterframework.efamily.server.core.ChannelManager;

import com.winterframework.efamily.server.core.TokenManager;

import com.winterframework.efamily.server.notification.INotificationService;
 
@Controller("unbindDeviceController")
@RequestMapping("/server")
public class UnbindDeviceController {
	private static final Logger log = LoggerFactory.getLogger(UnbindDeviceController.class);
	@Resource(name = "notificationServiceImpl")
	private INotificationService notificationService;

	
	@RequestMapping("/removeToken")
	@ResponseBody
	public Object push(@RequestBody Request<Map<String,Long>> req) {
		Response<Map<String,String>> res=new Response<Map<String,String>>(req);  
		try {
			log.info("解绑手表，清除TOKEN , CHANNEL ： userId = "+req.getData().get("userId")+" ; watchId = "+req.getData().get("deviceId")+" ; ");
			TokenManager.remove(Long.valueOf(req.getData().get("userId")), Long.valueOf(req.getData().get("deviceId")));
			ChannelManager.remove(Long.valueOf(req.getData().get("userId")), Long.valueOf(req.getData().get("deviceId")));
			res.setStatus(new Status(0));
		} catch (Exception e) {
			log.error("解绑手表，清除TOKEN失败"+e.getMessage(),e);
			res.setStatus(new Status(-1));
		}
		return res;
	}
}
