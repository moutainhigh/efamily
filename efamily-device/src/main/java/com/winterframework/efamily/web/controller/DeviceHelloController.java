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
import com.winterframework.efamily.dto.UpdateUserLoginRecordRequest;
import com.winterframework.efamily.dto.UpdateUserLoginRecordResponse;
import com.winterframework.efamily.dto.device.DeviceHelloNewRequest;
import com.winterframework.efamily.dto.device.DeviceHelloNewResponse;
import com.winterframework.efamily.dto.device.DeviceHelloRequest;
import com.winterframework.efamily.dto.device.DeviceHelloResponse;
import com.winterframework.efamily.entity.EjlUserLoginRecord;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IDeviceHelloService;
 
@Controller("deviceHelloController")
@RequestMapping("/device/system")
public class DeviceHelloController {
	private static final Logger log= LoggerFactory.getLogger(DeviceHelloController.class);
	
	@Resource(name = "deviceHelloServiceImpl")
	private IDeviceHelloService deviceHelloService;
	
	@RequestMapping("/hello")
	@ResponseBody
	public Object hello(@RequestBody Request<DeviceHelloRequest> req) throws BizException{
		log.info("request url /device/system/hello.");
		DeviceHelloRequest bizReq=req.getData();
		Response<DeviceHelloResponse> res=new Response<DeviceHelloResponse>(req);
		DeviceHelloResponse bizRes=null;
		bizRes=deviceHelloService.hello(req.getCtx(),bizReq,req.getData().getToken());
		res.setData(bizRes);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/helloNew")
	@ResponseBody
	public Object helloNew(@RequestBody Request<DeviceHelloNewRequest> req) throws BizException{
		DeviceHelloNewRequest bizReq=req.getData();
		Response<DeviceHelloNewResponse> res=new Response<DeviceHelloNewResponse>(req);
		DeviceHelloNewResponse bizRes=deviceHelloService.helloNew(req.getCtx(),bizReq);
		res.setData(bizRes);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	
	@RequestMapping("/loginRecord")
	@ResponseBody
	public Object loginRecord(@RequestBody Request<UpdateUserLoginRecordRequest> req) throws BizException{ 
		UpdateUserLoginRecordRequest bizReq=req.getData();
		Response<UpdateUserLoginRecordResponse> res=new Response<UpdateUserLoginRecordResponse>(req);
		UpdateUserLoginRecordResponse bizRes=new UpdateUserLoginRecordResponse();
		EjlUserLoginRecord loginRecord=DTOConvert.toUserLoginRecord(req.getUserId(), req.getDeviceId(), bizReq);
		deviceHelloService.loginRecord(req.getCtx(), loginRecord);
		res.setData(bizRes);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	
}
