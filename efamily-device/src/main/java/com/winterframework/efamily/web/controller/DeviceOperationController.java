package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.DeviceOperationRequest;
import com.winterframework.efamily.dto.DeviceOperationResponse;
import com.winterframework.efamily.service.IDeviceOperationService;
 
@Controller("deviceOperationController")
@RequestMapping("/device")
public class DeviceOperationController {
	@Resource(name = "deviceOperationServiceImpl")
	private IDeviceOperationService deviceOperationService;
	
	@RequestMapping("/operation")
	@ResponseBody
	public Object operation(@RequestBody Request<DeviceOperationRequest> req) throws BizException{
		if(null==req.getData()){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		deviceOperationService.operation(req.getCtx(),req.getData());
		Response<DeviceOperationResponse> res=new Response<DeviceOperationResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
