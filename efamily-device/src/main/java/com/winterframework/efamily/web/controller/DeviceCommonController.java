package com.winterframework.efamily.web.controller;

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
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceHardwareRequest;
import com.winterframework.efamily.dto.device.DeviceHardwareResponse;
import com.winterframework.efamily.dto.device.DeviceInitRequest;
import com.winterframework.efamily.dto.device.DeviceInitResponse;
import com.winterframework.efamily.dto.device.DeviceMobileRequest;
import com.winterframework.efamily.dto.device.DeviceMobileResponse;
import com.winterframework.efamily.dto.device.DeviceParamRequest;
import com.winterframework.efamily.dto.device.DeviceParamResponse;
import com.winterframework.efamily.dto.device.DevicePowerAutoRequest;
import com.winterframework.efamily.dto.device.DevicePowerAutoResponse;
import com.winterframework.efamily.dto.device.DeviceSoftwareRequest;
import com.winterframework.efamily.dto.device.DeviceSoftwareResponse;
import com.winterframework.efamily.dto.device.DeviceTimeRequest;
import com.winterframework.efamily.dto.device.DeviceTimeResponse;
import com.winterframework.efamily.dto.device.UpdateQrcodeResourceIdRequest;
import com.winterframework.efamily.dto.device.UpdateQrcodeResourceIdResponse;
import com.winterframework.efamily.entity.DeviceMobile;
import com.winterframework.efamily.entity.EfCustomer;
import com.winterframework.efamily.entity.EfPlatformDeviceSetting;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.enums.DeviceParameter;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceMobileService;
import com.winterframework.efamily.service.IDeviceParamConfigService;
import com.winterframework.efamily.service.IDeviceQrcodeService;
import com.winterframework.efamily.service.IEfComCustomerService;
import com.winterframework.efamily.service.IEfPlatformDeviceSettingService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IQrcodeService;
 
@Controller("deviceCommonController")
@RequestMapping("/device/common")
public class DeviceCommonController {
	private static final Logger log= LoggerFactory.getLogger(DeviceCommonController.class);
	@Resource(name = "deviceMobileServiceImpl")
	private IDeviceMobileService deviceMobileService;
	@Resource(name = "deviceParamConfigServiceImpl")
	private IDeviceParamConfigService deviceParamConfigService;
	@Resource(name = "deviceQrcodeServiceImpl")
	private IDeviceQrcodeService deviceQrcodeService; 
	@Resource(name="qrcodeServiceImpl")
	private IQrcodeService qrcodeService;
	@Resource(name="efPlatformDeviceSettingServiceImpl")
	private IEfPlatformDeviceSettingService efPlatformDeviceSettingService;
	@Resource(name="efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerService;
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@RequestMapping("/hardware")
	@ResponseBody
	public Object hardware(@RequestBody Request<DeviceHardwareRequest> req) throws BizException{ 
		DeviceHardwareRequest bizReq=req.getData(); 
		DeviceHardwareResponse deviceHardwareResponse = new DeviceHardwareResponse();
		String qrcodeUrl = deviceQrcodeService.getQrcodeUrl(bizReq.getImei());  
		deviceHardwareResponse.setQrcodeUrl(qrcodeUrl);
		Response<DeviceHardwareResponse> res=new Response<DeviceHardwareResponse>(req);
		res.setData(deviceHardwareResponse);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("v2.0/hardware")
	@ResponseBody
	public Object hardwareV20(@RequestBody Request<DeviceHardwareRequest> req) throws BizException{ 
		Context ctx=req.getCtx();
		DeviceHardwareRequest bizReq=req.getData();
		if(null==ctx || null==bizReq){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		deviceQrcodeService.upload(ctx,bizReq.getImei(), bizReq.getImsi(), bizReq.getModel(),bizReq.getType(),bizReq.getSimStatus());
		Response<DeviceHardwareResponse> res=new Response<DeviceHardwareResponse>(req);
		DeviceHardwareResponse deviceHardwareResponse = new DeviceHardwareResponse();
		res.setData(deviceHardwareResponse);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/qrcodeUrl")
	@ResponseBody
	public Object qrcodeUrl(@RequestBody Request<String> req) throws BizException{ 
		String imei=req.getData();
		String qrcodeUrl = deviceQrcodeService.getQrcodeUrl(imei);  
		Response<String> res=new Response<String>(req);
		res.setData(qrcodeUrl);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/qrcode")
	@ResponseBody
	public Object updateQrcodeResourceId(@RequestBody Request<UpdateQrcodeResourceIdRequest> req) throws BizException{ 
		UpdateQrcodeResourceIdRequest bizReq=req.getData(); 
		UpdateQrcodeResourceIdResponse updateQrcodeResourceIdResponse = new UpdateQrcodeResourceIdResponse();
		int updateRows = deviceQrcodeService.updateQrcodeResourceIdBy(req.getCtx(), bizReq.getImei(), bizReq.getResourceId());
		Response<UpdateQrcodeResourceIdResponse> res=new Response<UpdateQrcodeResourceIdResponse>(req);
		updateQrcodeResourceIdResponse.setUpdateRows(updateRows);
		res.setData(updateQrcodeResourceIdResponse);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/init")
	@ResponseBody
	public Object init(@RequestBody Request<DeviceInitRequest> req) throws BizException{
		DeviceInitRequest bizReq=req.getData();
		
		Qrcode qrcode=qrcodeService.getByImei(bizReq.getImei());
		if(null==qrcode){
			throw new BizException(StatusBizCode.DATA_NOT_EXIST.getValue());
		}
		
		EfPlatformDeviceSetting deviceSetting=efPlatformDeviceSettingService.getByDeviceType(qrcode.getType());
		if(null==deviceSetting){
			throw new BizException(StatusBizCode.DATA_NOT_EXIST.getValue());
		}
		Response<DeviceInitResponse> res=new Response<DeviceInitResponse>(req);
		DeviceInitResponse bizRes=new DeviceInitResponse();
		bizRes.setCommon(deviceSetting.getCommon());
		bizRes.setConnect(deviceSetting.getConnect());
		bizRes.setHealth(deviceSetting.getHealth());
		bizRes.setFrequency(deviceSetting.getFrequency());
		res.setData(bizRes);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		
		return res;
	}
	
	@RequestMapping("/param/upload")
	@ResponseBody
	public Object paramUpload(@RequestBody Request<DeviceParamRequest> req) throws BizException{ 
		DeviceParamRequest bizReq=req.getData(); 
		deviceParamConfigService.save(req.getCtx(),DTOConvert.toDeviceParamConfig(req.getUserId(),req.getDeviceId(),bizReq));
		Response<DeviceParamResponse> res=new Response<DeviceParamResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	
	@RequestMapping("/software/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<DeviceSoftwareRequest> req) throws BizException{
		DeviceSoftwareRequest bizReq=req.getData();
		
		if(null!=bizReq.getImei()){	//首次设备软件版本上传 
			Qrcode qrcode=qrcodeService.getByImei(bizReq.getImei());
			if(null==qrcode){
				throw new BizException(StatusBizCode.DATA_NOT_EXIST.getValue());
			}
			qrcode.setSoftwareVersion(bizReq.getVersion());
			qrcodeService.save(req.getCtx(), qrcode);
			
			//如果先绑设备 后打开设备则需要更新设备信息？
			EjlDevice device=ejlComDeviceService.getByImei(qrcode.getImei());
			if(null!=device){
				ejlComDeviceService.updateSoftwareVersion(req.getCtx(), device.getUserId(), device.getId(), bizReq.getVersion());
			}
		}else{	//服务器软件版本查询 时更新
			ejlComDeviceService.updateSoftwareVersion(req.getCtx(), req.getUserId(), req.getDeviceId(), bizReq.getVersion());
		}
		Response<DeviceSoftwareResponse> res=new Response<DeviceSoftwareResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/time")
	@ResponseBody
	public Object time(@RequestBody Request<String> req) throws BizException{
		String imei=req.getData();
		
		Qrcode qrcode=qrcodeService.getByImei(imei);
		if(null==qrcode){
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue());
		}
		EfCustomer customer=efComCustomerService.getEfCustomerBy(qrcode.getCustomerNumber());
		
		if(null==customer){
			throw new BizException(StatusBizCode.DEVICE_CUSTOMER_NO_EXIST.getValue());
		}
		
		Response<Long> res=new Response<Long>(req);
		res.setData(DateUtils.addSeconds(DateUtils.currentDate(), 2).getTime());
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	
	@RequestMapping("/mobile/upload")
	@ResponseBody
	public Object receive(@RequestBody Request<List<DeviceMobileRequest>> req) throws BizException{
		List<DeviceMobileRequest> bizReqList=req.getData();
		if(null==bizReqList){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		for(DeviceMobileRequest bizReq:bizReqList){
			log.debug("Mobile: mnc="+bizReq.getMnc()+" mcc="+bizReq.getMcc()+" lac="+bizReq.getLac1()
					+" ci="+bizReq.getCi1()+" rssi="+bizReq.getRssi1());
			DeviceMobile deviceMobile = DTOConvert.toDeviceMobile(req.getUserId(),req.getDeviceId(), bizReq);
			deviceMobileService.save(req.getCtx(),deviceMobile);	
		}
		Response<DeviceMobileResponse> res=new Response<DeviceMobileResponse>(req); 
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/power/auto/upload")
	@ResponseBody
	public Object powerAutoUpload(@RequestBody Request<DevicePowerAutoRequest> req) throws BizException{
		DevicePowerAutoRequest bizReq=req.getData(); 
		log.debug("Power auto:");
		
		EjlDeviceParmConfig param=new EjlDeviceParmConfig();
		param.setDeviceId(req.getDeviceId());
		param.setParamKey(DeviceParameter.POWER_AUTO.getValue());
		param.setParamValue(JsonUtils.toJson(bizReq)); 
		deviceParamConfigService.checkAndSetting(req.getCtx(),param);
		Response<DevicePowerAutoResponse> res=new Response<DevicePowerAutoResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/time/upload")
	@ResponseBody
	public Object timeUpload(@RequestBody Request<DeviceTimeRequest> req) throws BizException{
		DeviceTimeRequest bizReq=req.getData(); 
		log.debug("Time : "+bizReq.getTime());
		bizReq.getTime();
		Response<DeviceTimeResponse> res=new Response<DeviceTimeResponse>(req);
		try {
			//?????deviceParamConfigService.save(JsonUtils.toJson(bizReq));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BizException(StatusCode.SAVE_FAILED.getValue(),new String[]{req.getDeviceId()+""});
		}
		res.setData(new DeviceTimeResponse());
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
