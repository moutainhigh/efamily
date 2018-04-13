package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.winterframework.efamily.dto.device.DeviceBatteryRecordRequest;
import com.winterframework.efamily.dto.device.DeviceBatteryRecordResponse;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IDeviceBatteryService;

@Controller("deviceBatteryController")
@RequestMapping("/device/battery")
public class DeviceBatteryController {
	@Resource(name = "deviceBatteryServiceImpl")
	private IDeviceBatteryService deviceBatteryService; 
	
	private static final Logger log = LoggerFactory.getLogger(DeviceBatteryController.class);

	@RequestMapping("/query")
	@ResponseBody
	public Object query(@RequestBody Request<List<DeviceBatteryRecordRequest>> req) throws BizException {
		log.debug("welcome to battery controller.");
		List<DeviceBatteryRecordRequest> bizReqs = req.getData();
		if(null==bizReqs){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		List<DeviceBatteryRecord> batteryList=new ArrayList<DeviceBatteryRecord>(); 
		for(DeviceBatteryRecordRequest bizReq:bizReqs){ 
			batteryList.add(DTOConvert.toDeviceBatteryRecord(req.getDeviceId(),bizReq));
		}
		deviceBatteryService.save(req.getCtx(),req.getUserId(),req.getDeviceId(),batteryList); 
		Response<DeviceBatteryRecordResponse> res=new Response<DeviceBatteryRecordResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		log.debug("end battery controller.");
		return res;
	}
}
