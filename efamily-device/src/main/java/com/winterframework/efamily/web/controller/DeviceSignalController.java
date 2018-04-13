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
import com.winterframework.efamily.dto.device.DeviceSignalRecordRequest;
import com.winterframework.efamily.dto.device.DeviceSignalRecordResponse;
import com.winterframework.efamily.entity.DeviceSignalRecord;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IDeviceSignalService;
 
@Controller("deviceSignalController")
@RequestMapping("/device/common/signal")
public class DeviceSignalController {
	protected Logger log = LoggerFactory.getLogger(DeviceSignalController.class);
	
	@Resource(name = "deviceSignalServiceImpl")
	private IDeviceSignalService deviceSignalService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<List<DeviceSignalRecordRequest>> req) throws BizException{
		log.debug("welcome to signal controller");
		List<DeviceSignalRecordRequest> bizReqList=req.getData();
		if(null==bizReqList){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		List<DeviceSignalRecord> signalList=new ArrayList<DeviceSignalRecord>();
		for(DeviceSignalRecordRequest bizReq:bizReqList){
			signalList.add(DTOConvert.toDeviceSignalRecord(req.getDeviceId(),bizReq));
		}
		deviceSignalService.save(req.getCtx(),req.getUserId(),req.getDeviceId(),signalList);
		Response<DeviceSignalRecordResponse> res=new Response<DeviceSignalRecordResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
