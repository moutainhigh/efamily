package com.winterframework.efamily.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.HealthyProfileStruc;
import com.winterframework.efamily.dto.HealthyProfilesRequest;
import com.winterframework.efamily.dto.HealthyProfilesResponse;
import com.winterframework.efamily.service.IEjlHealthManageService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.utils.NotificationUtil;


/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("healthyProfilesController")
@RequestMapping("/server")
public class HealthyProfilesController{
	private static final Logger logger = LoggerFactory.getLogger(HealthyProfilesController.class);

	@Resource(name = "ejlHealthManageServiceImpl")
	private IEjlHealthManageService ejlHealthManageServiceImpl;
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService 	ejlUserDeviceServiceImpl;
	@Resource(name="notificationUtil")
	protected NotificationUtil notificationUtil;

	@RequestMapping("/healthyProfiles")
	@ResponseBody
	protected Response<HealthyProfilesResponse> doHandle(@RequestBody Request<HealthyProfilesRequest> request) throws Exception {
		Response<HealthyProfilesResponse> fmlResponse = new Response<HealthyProfilesResponse>(request);
		HealthyProfilesResponse healthyProfilesResponse = new HealthyProfilesResponse();
		
        HealthyProfileStruc list;
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper(); 
		try{
			Notification notification = new Notification();
			NotyTarget notyTarget = new NotyTarget();
			notyTarget.setUserId(request.getData().getUserId());
			Long deviceId = request.getData().getDeviceId();
			if(request.getData().getDeviceId() == null){
			deviceId = ejlUserDeviceServiceImpl.getUserUseingDeviceId(request.getData().getUserId());}
			notyTarget.setDeviceId(deviceId);
			notification.setTarget(notyTarget);
			NotyMessage message = new NotyMessage();
			message.setType(NotyMessage.Type.OPER);
			message.setCommand(20825);
			Map<String,String> data=new HashMap<String,String>();
			data.put("command", "20825");
			message.setData(data);
			notification.setMessage(message);
			//屏蔽 notificationUtil.notification(notification);
			data.put("command", "20725");
			message.setCommand(20725);
			//屏蔽 notificationUtil.notification(notification); 
		
		list = ejlHealthManageServiceImpl.getHealthyProfilesByFamilyId(request.getData().getDeviceId(),
				request.getData().getUserId() == null ? null : request.getData().getUserId());
		healthyProfilesResponse.setHealthyProfileList(mapper.writeValueAsString(list));
		fmlResponse.setData(healthyProfilesResponse);
		logger.info(mapper.writeValueAsString(list));
		}catch(Exception e){
			logger.error("query healthy from device error", e);
		} 
		return fmlResponse;
	}
}
