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
import com.winterframework.efamily.dto.device.HealthStepCountStruc;
import com.winterframework.efamily.entity.EjlHealthStepCount;


import com.winterframework.efamily.service.IDeviceStepService;

@Controller("deviceStepController")
@RequestMapping("/device/step")
public class DeviceStepController {
	@Resource(name = "deviceStepServiceImpl")
	private IDeviceStepService deviceStepServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(DeviceStepController.class);

	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<List<HealthStepCountStruc>> req) throws BizException {
		List<HealthStepCountStruc> listStruc = req.getData();
		if(null==listStruc){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		List<EjlHealthStepCount> list = new ArrayList<EjlHealthStepCount>();
		for (HealthStepCountStruc struc : listStruc) {
			EjlHealthStepCount dbCount = new EjlHealthStepCount();
			dbCount.setBegintime(struc.getBegintime());
			dbCount.setEndtime(struc.getEndtime());
			dbCount.setDeviceId(req.getDeviceId());
			dbCount.setUserId(struc.getUserId());
			dbCount.setSteps(struc.getSteps());
			dbCount.setType(struc.getType());
			dbCount.setHeight(struc.getHeight());
			dbCount.setCalorie(struc.getCalorie());
			list.add(dbCount);
		}
		Response<StepResponse> res = new Response<StepResponse>(req);
		try {
			res.setData(deviceStepServiceImpl.save(req.getCtx(),req.getUserId(), req.getDeviceId(), list));
			res.setStatus(new Status(StatusCode.OK.getValue()));
		} catch (Exception e) {
			logger.error("/device/step/upload error", e);
			res.setStatus(new Status(StatusCode.HTTP.getValue()));
		}
		return res;
	}
}
