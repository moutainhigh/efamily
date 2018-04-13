package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.winterframework.efamily.dto.device.DeviceBloodPressureRequest;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IDeviceBloodService;
import com.winterframework.efamily.service.IEfComDeviceHealthyDataPushService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComHealthHeartRateService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.service.impl.DeviceBloodServiceImpl;
import com.winterframework.modules.spring.exetend.PropertyConfig;

 
@Controller("deviceBloodPressureController")
@RequestMapping("/device/health/bloodPressure")
public class DeviceBloodPressureController {

	@Resource(name = "deviceBloodServiceImpl")
	private IDeviceBloodService deviceBloodService;
	
	@Resource(name="efComDeviceHealthyDataPushServiceImpl")
	protected IEfComDeviceHealthyDataPushService efComDeviceHealthyDataPushServiceImpl;
	
	@Resource(name = "ejlComHealthHeartRateServiceImpl")
	private IEjlComHealthHeartRateService ejlComHealthHeartRateService;
	
	@Resource(name="qrcodeServiceImpl")
	protected IQrcodeService qrcodeServiceImpl;	
	
	@Resource(name="ejlComDeviceServiceImpl")
	protected IEjlComDeviceService ejlComDeviceServiceImpl;	
	
	@PropertyConfig(value="customer.healthy.data.push")
	private String customerHealthyDataPush;
	
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<List<DeviceBloodPressureRequest>> req) throws BizException{
		List<DeviceBloodPressureRequest> bizReqList=req.getData();
		List<EjlHealthBloodPressure> bloodList=new ArrayList<EjlHealthBloodPressure>();
		Context ctx = new Context();
		ctx.set("userId", req.getUserId());
		for(DeviceBloodPressureRequest bizReq:bizReqList){
			EjlHealthBloodPressure ejlHealthBloodPressure = DTOConvert.toBloodPressure(req.getUserId(),req.getDeviceId(), bizReq);
			bloodList.add(ejlHealthBloodPressure);
		}
		deviceBloodService.save(ctx, bloodList);
		List<EjlHealthHeartRate> ejlHealthHeartRateList =  DeviceBloodServiceImpl.getHeartRateList(bloodList);
		ejlComHealthHeartRateService.save(ctx, ejlHealthHeartRateList);
		
		//*** 判断该设备是否需要进行健康数据推送
		EjlDevice ejlDevice = ejlComDeviceServiceImpl.get(req.getDeviceId());
		Qrcode qrcode = qrcodeServiceImpl.getByImei(ejlDevice.getCode());
		
		Integer customerNumber = qrcode.getCustomerNumber();
		if(customerNumber!=null && customerHealthyDataPush.contains(String.valueOf(customerNumber))){
			efComDeviceHealthyDataPushServiceImpl.saveBloodPressureToDataPush(req.getUserId(),req.getDeviceId(), qrcode.getImei(), bloodList);
			efComDeviceHealthyDataPushServiceImpl.saveHeartToDataPush(req.getUserId(),req.getDeviceId(), qrcode.getImei(), ejlHealthHeartRateList);
		}
		
		deviceBloodService.notifyForHeartUpdate(req.getCtx(),req.getUserId(), bloodList);
		Response<HeartResponse> res=new Response<HeartResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	

}
