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
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.dto.device.DeviceSittingRequest;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthSitting;
import com.winterframework.efamily.entity.EjlUserExtendInfo;
import com.winterframework.efamily.service.IDeviceSittingService;
import com.winterframework.efamily.service.IEjlComUserExtendInfoService;

@Controller("deviceSittingController")
@RequestMapping("/device/sitting")
public class DeviceSittingController { 
	@Resource(name = "deviceSittingServiceImpl")
	private IDeviceSittingService deviceSittingServiceImpl;
	
	@Resource(name = "ejlComUserExtendInfoServiceImpl")
	private IEjlComUserExtendInfoService ejlComUserExtendInfoServiceImpl;
	

	private static final Logger log = LoggerFactory.getLogger(DeviceSittingController.class);

	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<DeviceSittingRequest> req) throws BizException {
		DeviceSittingRequest bizReq = req.getData();
		if(null==bizReq){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		EjlUserExtendInfo ejlUserExtendInfoParam = new EjlUserExtendInfo();
		ejlUserExtendInfoParam.setUserId(req.getCtx().getUserId());
		EjlUserExtendInfo  ejlUserExtendInfo =ejlComUserExtendInfoServiceImpl.selectOneObjByAttribute(req.getCtx(), ejlUserExtendInfoParam);
		Float sittingTime = null;
		if(ejlUserExtendInfo != null) {
			sittingTime= ejlUserExtendInfo.getSitTime();
		}
		List<EjlHealthSitting> list = new ArrayList<EjlHealthSitting>();
		List<Long> toTimeList=bizReq.getToTimeList();
		for (Long toTime : toTimeList) {
			EjlHealthSitting sitting = new EjlHealthSitting();
			if(sittingTime != null) {
				sitting.setStartTime(DateUtils.addMinutes(DateUtils.getDate(toTime),-sittingTime.intValue()));
			}
			sitting.setEndTime(DateUtils.getDate(toTime));
			sitting.setUserId(req.getUserId());
			sitting.setDeviceId(req.getDeviceId());
			list.add(sitting);
		}
		Response<StepResponse> res = new Response<StepResponse>(req); 
		res.setData(deviceSittingServiceImpl.save(req.getCtx(),req.getUserId(), req.getDeviceId(), list));
		res.setStatus(new Status(StatusCode.OK.getValue())); 
		return res;
	}
	
	@RequestMapping("/sitSwitchNotice")
	@ResponseBody
	public Object sitSwitchNotice(@RequestBody Request<Integer> req) throws BizException{
		EjlDevice ejlDevice = new EjlDevice();
		ejlDevice.setId(req.getDeviceId());
		ejlDevice.setSleeplockStatus(1);
		Context ctx = new Context();
		ctx.set("userId", req.getUserId());
		Response<StepResponse> res=new Response<StepResponse>(req);
		try{
		deviceSittingServiceImpl.saveSitSwitch(ctx, req.getUserId(), req.getDeviceId(), req.getData()-1);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		}catch(Exception e){
		log.error("get device user info error",e);
		res.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		
		return res;
	}
}
