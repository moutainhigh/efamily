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
import com.winterframework.efamily.dto.app.AppDeviceHealthHeartLatestRequest;
import com.winterframework.efamily.dto.app.AppDeviceHealthHeartLatestResponse;
import com.winterframework.efamily.dto.app.AppDeviceHealthHeartMeasureRequest;
import com.winterframework.efamily.dto.app.AppDeviceHealthHeartMeasureResponse;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.service.IAppDeviceHealthHeartService;

 
@Controller("deviceHealthHeartController")
@RequestMapping("/device/health/heart")
public class DeviceHealthHeartController {
	private static final Logger log = LoggerFactory.getLogger(DeviceHealthHeartController.class);
	
	@Resource(name = "appDeviceHealthHeartServiceImpl")
	private IAppDeviceHealthHeartService appDeviceHealthHeartService;
	
	@RequestMapping("/latest")
	@ResponseBody
	protected Response<AppDeviceHealthHeartLatestResponse> latest(@RequestBody Request<AppDeviceHealthHeartLatestRequest> request) throws BizException{
		Long userId=request.getData().getUserId();
		Long deviceId=request.getData().getDeviceId();
		Response<AppDeviceHealthHeartLatestResponse> response = new Response<AppDeviceHealthHeartLatestResponse>(request);
		EjlHealthHeartRate heartRate=appDeviceHealthHeartService.queryLatest(request.getCtx(),userId,deviceId);
		AppDeviceHealthHeartLatestResponse bizRes=new AppDeviceHealthHeartLatestResponse();
		if(heartRate!=null){
			bizRes.setValue(heartRate.getRate().intValue());
		}
		response.setStatus(new Status(StatusCode.OK.getValue()));
		response.setData(bizRes);
		return response;
	}
	@RequestMapping("/measure")
	@ResponseBody
	protected Response<AppDeviceHealthHeartMeasureResponse> measure(@RequestBody Request<AppDeviceHealthHeartMeasureRequest> request) throws BizException{
		AppDeviceHealthHeartMeasureRequest bizReq=request.getData();
		Long userId=bizReq.getUserId();
		Long deviceId=bizReq.getDeviceId();
		Integer type=bizReq.getType();
		Response<AppDeviceHealthHeartMeasureResponse> response = new Response<AppDeviceHealthHeartMeasureResponse>(request);
		if(type==1){
			appDeviceHealthHeartService.start(request.getCtx(),userId, deviceId);
		}else if(type==2){
			appDeviceHealthHeartService.stop(request.getCtx(),userId, deviceId);
		}
		response.setStatus(new Status(StatusCode.OK.getValue()));
		return response;
	}
	
}