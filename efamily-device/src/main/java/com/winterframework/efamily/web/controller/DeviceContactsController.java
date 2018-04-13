package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.winterframework.efamily.dto.device.DeviceContactsRequest;
import com.winterframework.efamily.dto.device.DeviceContactsResponse;
import com.winterframework.efamily.entity.EjlDeviceAddressList;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IDeviceContactsService;
 
@Controller("deviceContactsController")
@RequestMapping("/device/contacts")
public class DeviceContactsController {
	@Resource(name = "deviceContactsServiceImpl")
	private IDeviceContactsService deviceContactsService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<List<DeviceContactsRequest>> req) throws BizException{ 
		Response<DeviceContactsResponse> res=new Response<DeviceContactsResponse>(req);
		List<DeviceContactsRequest> bizReqList=req.getData();
		if(null==bizReqList){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		List<EjlDeviceAddressList> contactList=new ArrayList<EjlDeviceAddressList>();
		for(DeviceContactsRequest bizReq:bizReqList){
			contactList.add(DTOConvert.toDeviceContacts(req.getUserId(),req.getDeviceId(), bizReq));
		}
		deviceContactsService.upload(req.getCtx(),contactList);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
