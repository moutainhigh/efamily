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
import com.winterframework.efamily.dto.app.AppDeviceSoftwareQueryRequest;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareQueryResponse;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareUpdateRequest;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareUpdateResponse;
import com.winterframework.efamily.service.IEjlDeviceService;

 
@Controller("deviceCommonController")
@RequestMapping("/device")
public class DeviceCommonController {
	private static final Logger log = LoggerFactory.getLogger(DeviceCommonController.class);
	
	@Resource(name = "ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceService;
	
	
	@RequestMapping("/querySoftware")
	@ResponseBody
	protected Response<AppDeviceSoftwareQueryResponse> querySoftware(@RequestBody Request<AppDeviceSoftwareQueryRequest> request) throws BizException{
		Long deviceId=request.getData().getDeviceId();
		Response<AppDeviceSoftwareQueryResponse> response = new Response<AppDeviceSoftwareQueryResponse>(request);
		AppDeviceSoftwareQueryResponse bizRes=ejlDeviceService.querySoftware(request.getCtx(),deviceId);
		response.setStatus(new Status(StatusCode.OK.getValue()));
		response.setData(bizRes);
		return response;
	}
	@RequestMapping("/updateSoftware")
	@ResponseBody
	protected Response<AppDeviceSoftwareUpdateResponse> updateSoftware(@RequestBody Request<AppDeviceSoftwareUpdateRequest> request) throws BizException{
		Long deviceId=request.getData().getDeviceId();
		Response<AppDeviceSoftwareUpdateResponse> response = new Response<AppDeviceSoftwareUpdateResponse>(request);
		AppDeviceSoftwareUpdateResponse bizRes=ejlDeviceService.updateSoftware(request.getCtx(),deviceId);
		response.setStatus(new Status(StatusCode.OK.getValue()));
		response.setData(bizRes);
		return response;
	}
}