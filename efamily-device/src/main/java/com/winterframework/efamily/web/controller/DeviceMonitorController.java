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
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.dto.device.UserDeviceMonitorStruc;
import com.winterframework.efamily.entity.EjlDeviceMonitor;
import com.winterframework.efamily.service.IDeviceMonitorService;

@Controller("deviceMonitorController")
@RequestMapping("/device/monitor")
public class DeviceMonitorController {
	@Resource(name = "deviceMonitorServiceImpl")
	private IDeviceMonitorService deviceMonitorImpl;

	private static final Logger logger = LoggerFactory.getLogger(DeviceMonitorController.class);

	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<UserDeviceMonitorStruc> req) throws BizException {
		UserDeviceMonitorStruc struc = req.getData();
		if(null==struc){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		EjlDeviceMonitor ejlDevice = new EjlDeviceMonitor();
		ejlDevice.setStartTime(struc.getStartTime());
		ejlDevice.setEndTime(struc.getEndTime());
		ejlDevice.setDeviceId(req.getDeviceId());
		ejlDevice.setUserId(struc.getUserId());
		ejlDevice.setDeviceUserId(struc.getDeviceUserId());
		ejlDevice.setStatus(struc.getStatus());
		Response<StepResponse> res = new Response<StepResponse>(req);
		try {
			res.setData(deviceMonitorImpl.saveOrUpdate(req.getCtx(),req.getUserId(), req.getDeviceId(), ejlDevice));
			res.setStatus(new Status(StatusCode.OK.getValue()));
		} catch (Exception e) {
			logger.error("/device/monitor/upload error", e);
			res.setStatus(new Status(StatusCode.HTTP.getValue()));
		}
		return res;
	}
}
