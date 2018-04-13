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
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.HeartResponse;
import com.winterframework.efamily.dto.SleepRequest;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthSleep;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IDeviceSleepService;
import com.winterframework.efamily.service.IEjlComDeviceService;
 
@Controller("deviceSleepController")
@RequestMapping("/device/health/sleep")
public class DeviceSleepController {
	@Resource(name = "deviceSleepServiceImpl")
	private IDeviceSleepService deviceSleepService;
	
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceServiceImpl;
	
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<List<SleepRequest>> req) throws BizException{
		List<SleepRequest> bizReqList=req.getData();
		List<EjlHealthSleep> sleepList=new ArrayList<EjlHealthSleep>();
		for(SleepRequest bizReq:bizReqList){
			EjlHealthSleep ejlHealthSleep = DTOConvert.toSleep(req.getUserId(),req.getDeviceId(), bizReq);
			if(ejlHealthSleep.getFromTime()!=null&&ejlHealthSleep.getFromTime().equals(ejlHealthSleep.getToTime())){
				continue;
			}
			sleepList.add(ejlHealthSleep);
			EjlDevice ejlDevice = ejlComDeviceServiceImpl.get(req.getDeviceId());
			if(ejlDevice.getSleeplockStatus().intValue()==1){
				ejlDevice = new EjlDevice();
				ejlDevice.setId(req.getDeviceId());
				ejlDevice.setSleeplockStatus(0);
				Context ctx = new Context();
				ctx.set("userId", req.getUserId());
				ejlComDeviceServiceImpl.save(ctx, ejlDevice);
				deviceSleepService.notifyForSleepStart(req.getUserId(), req.getDeviceId(),2);
			}
		}
		deviceSleepService.save(req.getCtx(),req.getUserId(),req.getDeviceId(),sleepList);
		Response<HeartResponse> res=new Response<HeartResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	
	@RequestMapping("/startSleep")
	@ResponseBody
	public Object startSleep(@RequestBody Request<SleepRequest> req) throws BizException{
		EjlDevice ejlDevice = new EjlDevice();
		ejlDevice.setId(req.getDeviceId());
		ejlDevice.setSleeplockStatus(1);
		Context ctx = new Context();
		ctx.set("userId", req.getUserId());
		ejlComDeviceServiceImpl.save(ctx, ejlDevice);
		deviceSleepService.notifyForSleepStart(req.getUserId(), req.getDeviceId(),1);
		Response<HeartResponse> res=new Response<HeartResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
