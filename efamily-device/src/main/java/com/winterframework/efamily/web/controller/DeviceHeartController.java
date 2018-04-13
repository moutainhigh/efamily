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
import com.winterframework.efamily.dto.HeartRequest;
import com.winterframework.efamily.dto.HeartResponse;
import com.winterframework.efamily.dto.device.DeviceHeartFinishRequest;
import com.winterframework.efamily.dto.device.DeviceHeartFinishResponse;
import com.winterframework.efamily.dto.device.DeviceHeartStartRequest;
import com.winterframework.efamily.dto.device.DeviceHeartStartResponse;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceHeartService;
import com.winterframework.efamily.service.IEfComDeviceHealthyDataPushService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
@Controller("deviceHeartController")
@RequestMapping("/device/health/heart")
public class DeviceHeartController {
	@Resource(name = "deviceHeartServiceImpl")
	private IDeviceHeartService deviceHeartService;
	
	@Resource(name="qrcodeServiceImpl")
	protected IQrcodeService qrcodeServiceImpl;	
	
	@Resource(name="efComDeviceHealthyDataPushServiceImpl")
	protected IEfComDeviceHealthyDataPushService efComDeviceHealthyDataPushServiceImpl;
	
	@PropertyConfig(value="customer.healthy.data.push")
	private String customerHealthyDataPush;
	
	@Resource(name="ejlComDeviceServiceImpl")
	protected IEjlComDeviceService ejlComDeviceServiceImpl;	
	
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<List<HeartRequest>> req) throws BizException{
		List<HeartRequest> bizReqList=req.getData();
		List<EjlHealthHeartRate> heartList=new ArrayList<EjlHealthHeartRate>();
		for(HeartRequest bizReq:bizReqList){
			if(bizReq.getCount()<=0){
				continue;
			}
			heartList.add(DTOConvert.toHeartRate(req.getUserId(),req.getDeviceId(), bizReq));
		}
		deviceHeartService.save(req.getCtx(),req.getUserId(),req.getDeviceId(),heartList);
		
		//*** 判断该设备是否需要进行健康数据推送
		EjlDevice ejlDevice = ejlComDeviceServiceImpl.get(req.getDeviceId());
		Qrcode qrcode = qrcodeServiceImpl.getByImei(ejlDevice.getCode());
		
		Integer customerNumber = qrcode.getCustomerNumber();
		if(customerNumber!=null && customerHealthyDataPush.contains(String.valueOf(customerNumber))){
			efComDeviceHealthyDataPushServiceImpl.saveHeartToDataPush(req.getUserId(),req.getDeviceId(), qrcode.getImei(), heartList);
		}
		
		deviceHeartService.notifyForHeartUpdate(req.getCtx(),req.getUserId(), bizReqList);
		Response<HeartResponse> res=new Response<HeartResponse>(req);
		
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/start")
	@ResponseBody
	public Object start(@RequestBody Request<DeviceHeartStartRequest> req) throws BizException{
		DeviceHeartStartRequest bizReq=req.getData();
		if(bizReq==null){
			throw new BizException(StatusBizCode.PARAM_INCOMPLETE.getValue());
		}
		deviceHeartService.start(req.getCtx(), req.getUserId(), req.getDeviceId(), bizReq);
		Response<DeviceHeartStartResponse> res=new Response<DeviceHeartStartResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		
		return res;
	}
	@RequestMapping("/finish")
	@ResponseBody
	public Object finish(@RequestBody Request<DeviceHeartFinishRequest> req) throws BizException{
		DeviceHeartFinishRequest bizReq=req.getData();
		if(bizReq==null){
			throw new BizException(StatusBizCode.PARAM_INCOMPLETE.getValue());
		}
		deviceHeartService.finish(req.getCtx(), req.getUserId(), req.getDeviceId(), bizReq);
		Response<DeviceHeartFinishResponse> res=new Response<DeviceHeartFinishResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
