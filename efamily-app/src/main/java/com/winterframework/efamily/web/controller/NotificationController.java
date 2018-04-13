package com.winterframework.efamily.web.controller;
 

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.device.NotyTaskRequest;
import com.winterframework.efamily.dto.device.NotyTaskResponse;
import com.winterframework.efamily.entity.NotificationTask;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.INotificationTaskService;
 
@Controller("notificationController")
@RequestMapping("/notification")
public class NotificationController {
	private static final Logger log= LoggerFactory.getLogger(NotificationController.class);
	
	@Resource(name = "notificationTaskServiceImpl")
	private INotificationTaskService notificationTaskService;
	 
	@RequestMapping("/task_save")
	@ResponseBody
	public Object saveTask(@RequestBody Request<NotyTaskRequest> req) throws BizException{
		NotyTaskRequest bizReq=req.getData();

		NotificationTask task=DTOConvert.toNotificationTask(bizReq);//?????resourceService.getById(bizReq.getResourceId());
		notificationTaskService.save(req.getCtx(),task);
		Response<NotyTaskResponse> res=new Response<NotyTaskResponse>(req); 
		NotyTaskResponse bizRes=new NotyTaskResponse();
		bizRes.setId(task.getId());
		res.setData(bizRes);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
