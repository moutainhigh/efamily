package com.winterframework.efamily.web.controller;

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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.device.DeviceBindConfirmRequest;
import com.winterframework.efamily.dto.device.DeviceBindConfirmResponse;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.service.IDeviceBindService;
import com.winterframework.efamily.service.IEjlComUserDeviceService;
 
@Controller("deviceBindController")
@RequestMapping("/device/system")
public class DeviceBindController {
	private static final Logger log= LoggerFactory.getLogger(DeviceBindController.class);
	@Resource(name = "deviceBindServiceImpl")
	private IDeviceBindService deviceBindService;
	
	@Resource(name="ejlComUserDeviceServiceImpl")
	private IEjlComUserDeviceService ejlComUserDeviceService;
	
	@RequestMapping("/bind")
	@ResponseBody
	public Object bind(@RequestBody Request<DeviceBindConfirmRequest> req) throws BizException{
		Long userId =req.getUserId();
		Long deviceId =req.getDeviceId();
		DeviceBindConfirmRequest bizReq=req.getData();	
		Response<DeviceBindConfirmResponse> res=new Response<DeviceBindConfirmResponse>(req); 
		if(this.checkIsConfirm(req.getCtx(), userId, deviceId)){//机构用户不需要绑定确认
			Integer status=deviceBindService.bind(req.getCtx(),userId,deviceId,bizReq);
			//推送消息：手表绑定结果 toId-status
			try{
				deviceBindService.pushBind(req.getCtx(),userId, status);
			}catch(BizException e){
				//推送不回滚
				log.error("push bind error.",e);
			}
		}
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	
	private boolean checkIsConfirm(Context ctx,Long userId,Long deviceId) throws BizException{
		EjlUserDevice entity = new EjlUserDevice();
		entity.setDeviceId(deviceId);
		entity.setUserId(userId);
		entity.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
		EjlUserDevice ejlUserDevice = ejlComUserDeviceService.selectOneObjByAttribute(ctx, entity);
		return ejlUserDevice == null;
	}
}
