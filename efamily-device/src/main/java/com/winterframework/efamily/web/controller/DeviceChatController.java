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
import com.winterframework.efamily.dto.device.DeviceChatMessageRequest;
import com.winterframework.efamily.dto.device.DeviceChatMessageResponse;
import com.winterframework.efamily.dto.device.DeviceChatSettingRequest;
import com.winterframework.efamily.dto.device.DeviceChatSettingResponse;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IDeviceChatService;
 
@Controller("deviceChatController")
@RequestMapping("/device/chat")
public class DeviceChatController {
	@Resource(name = "deviceChatServiceImpl")
	private IDeviceChatService deviceChatService;
	
	@RequestMapping("/setting_upload")
	@ResponseBody
	public Object settingUpload(@RequestBody Request<DeviceChatSettingRequest> req) throws BizException{
		DeviceChatSettingRequest bizReq=req.getData(); 
		deviceChatService.settingUpload(req.getCtx(),bizReq);
		Response<DeviceChatSettingResponse> res=new Response<DeviceChatSettingResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/receive")
	@ResponseBody
	public Object receive(@RequestBody Request<DeviceChatMessageRequest> req) throws BizException{
		DeviceChatMessageRequest bizReq=req.getData();
		Response<DeviceChatMessageResponse> res=new Response<DeviceChatMessageResponse>(req);
		EjlMessage message=DTOConvert.toMessage(bizReq);
		deviceChatService.receive(req.getCtx(),req.getUserId(),req.getDeviceId(),message);
		res.setData(new DeviceChatMessageResponse());
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
