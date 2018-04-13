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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.DeviceBatteryInfo;
import com.winterframework.efamily.dto.LocationStruc;
import com.winterframework.efamily.dto.UserLocationRequest;
import com.winterframework.efamily.dto.UserLocationResponse;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlLocationService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 获取用户定位handler
 * 
 * @author david
 * 
 */
@Controller("userLocationController")
@RequestMapping("/server")
public class UserLocationController{

	@Resource(name = "ejlLocationServiceImpl")
	private IEjlLocationService ejlLocationServiceImpl;
	
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	@Resource(name="ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(UserLocationController.class);

	@RequestMapping("/userLocation")
	@ResponseBody
	protected Response<UserLocationResponse> doHandle(@RequestBody Request<UserLocationRequest> request) throws Exception {
		Response<UserLocationResponse> fmlResponse = new Response<UserLocationResponse>(request);
		UserLocationResponse userLocationResponse = new UserLocationResponse();
		
			 List<LocationStruc> response = ejlLocationServiceImpl.getUserLocation(
					 request.getData().getUserId(),
					 request.getData().getWatchId(),
					 request.getData().getRecentHourType(),
					 request.getData().getQueryType());
			 
			 if(request.getData().getQueryType().intValue()==1){
					try{
						Map<String,String> map = new HashMap<String,String>();
						map.put("userId", request.getData().getUserId()+"");
						map.put("deviceId", request.getData().getWatchId()+"");
						map.put("commond", "20626");
						ejlUserServiceImpl.pushDevice(request.getData().getUserId(),request.getData().getWatchId(), map,20626, NotyMessage.Type.OPER);
					}catch(Exception e){
						logger.error("user Locaiton query error",e);
					}
			 }   

			DeviceBatteryInfo deviceBatteryInfo =  deviceConfigServiceImpl.getDeviceBatteryInfo(request.getData().getWatchId(), "battery");
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			ObjectMapper mapper=new ObjectMapper();
			userLocationResponse.setLocation(mapper.writeValueAsString(response));
			userLocationResponse.setBatteryInfo(mapper.writeValueAsString(deviceBatteryInfo));
			EjlFamily family = ejlFamilyUserServiceImpl.getEjlFamilyByUserId(request.getData().getUserId());
			if(family!=null){
				userLocationResponse.setFamilyId(family.getId());
				userLocationResponse.setFamilyName(family.getName());
			}
			fmlResponse.setData(userLocationResponse);
		return fmlResponse;
	}
}
