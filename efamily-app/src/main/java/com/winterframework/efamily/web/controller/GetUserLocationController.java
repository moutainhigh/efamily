package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.DeviceBatteryInfo;
import com.winterframework.efamily.dto.GetUserLocationRequest;
import com.winterframework.efamily.dto.GetUserLocationResponse;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlLocationService;


@Controller("getUserLocationController")
@RequestMapping("/server")
public class GetUserLocationController{
	@Resource(name = "ejlLocationServiceImpl")
	private IEjlLocationService ejlLocationServiceImpl;
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	@Resource(name="ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;

	@RequestMapping("/getUserLocation")
	@ResponseBody
	protected Response<GetUserLocationResponse> doHandle(@RequestBody Request<GetUserLocationRequest> request) throws Exception {
		    Response<GetUserLocationResponse> fmlResponse = new Response<GetUserLocationResponse>(request);
		    Long userId=request.getData().getUserId();
		    Long deviceId=request.getData().getDeviceId();
		    Long time=request.getData().getTime();
		    GetUserLocationResponse getUserLocationResponse =  ejlLocationServiceImpl.getUserLastLocation(request.getCtx(),userId,deviceId,time);
			
			DeviceBatteryInfo deviceBatteryInfo =  deviceConfigServiceImpl.getDeviceBatteryInfo(request.getData().getDeviceId(), "battery");
			
			EjlDevice deviceObj = ejlDeviceServiceImpl.get(deviceId);
			getUserLocationResponse.setRunningMode(deviceObj.getRunningMode());
			getUserLocationResponse.setOnLineStatus(deviceObj.getOnlineStatus());
			
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			ObjectMapper mapper=new ObjectMapper();
			getUserLocationResponse.setBatteryInfo(mapper.writeValueAsString(deviceBatteryInfo));
			
			fmlResponse.setData(getUserLocationResponse);
		return fmlResponse;
	}
}
