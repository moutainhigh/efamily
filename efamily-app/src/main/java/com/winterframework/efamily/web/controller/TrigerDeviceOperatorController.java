package com.winterframework.efamily.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.TrigerDeviceRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IInstitutionUserService;
import com.winterframework.efamily.utils.NotificationUtil;

@Controller("trigerDeviceOperatorController")
@RequestMapping("/server")
public class TrigerDeviceOperatorController {
	private static final Logger log = LoggerFactory.getLogger(TrigerDeviceOperatorController.class);
	
	@Resource(name = "ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;
	
	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService 	ejlUserDeviceServiceImpl;
	
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	@Resource(name="notificationUtil")
	protected NotificationUtil notificationUtil;
	
	@Resource(name = "institutionUserServiceImpl")
	private IInstitutionUserService institutionUserServiceImpl;
	
	
	
	
	@RequestMapping("/heartTest")
	@ResponseBody
	protected Response<Object> heartTest(@RequestBody Request<List<String>> request) throws BizException {
		Response<Object> fmlResponse = new Response<Object>(request);
		List<String> list = request.getData();
		try{
			for(String imei:list){
				EjlDevice  ejlDevice   = deviceConfigServiceImpl.getEjlDeviceByCode(imei);
				if(ejlDevice != null){
					EjlUserDevice userDeviceObj = ejlUserDeviceServiceImpl.getEjlUserDeviceByDeviceIdAndStatus(ejlDevice.getId(),EfamilyConstant.USER_DEVICE_STATUS_USING);
		        	if(userDeviceObj == null){
		        		log.info("手表测试心率时，未绑定用户。 watchCode = "+imei );
		            	throw new BizException(StatusBizCode.USER_NOT_BIND_DEVICE.getValue());
		        	}else{
		        		String key = String.valueOf(request.getCtx().get("orgKey"));
		        		if(!institutionUserServiceImpl.isImeiContainOrg(imei,key)){
							log.error("IMEI_UKEY_ORGUKEY_NOTSAME imei:"+imei);
							throw new BizException(StatusBizCode.IMEI_UKEY_ORGUKEY_NOTSAME.getValue());
						}
		        		Notification notification = new Notification();
		        		NotyTarget notyTarget = new NotyTarget();
		        		notyTarget.setUserId(userDeviceObj.getUserId());
		        		notyTarget.setDeviceId(userDeviceObj.getDeviceId());
		        		notification.setTarget(notyTarget);
		        		NotyMessage message = new NotyMessage();
		        		message.setType(NotyMessage.Type.OPER);
		        		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		        		message.setCommand(20826);
		    			Map<String,String> data=new HashMap<String,String>();
		    			data.put("command", "20826");
		    			message.setData(data);
		    			notification.setMessage(message);
		    			notificationUtil.notification(notification);
		        	}
				}else{
					throw new BizException(StatusBizCode.DEVICE_KEY_INVALID.getValue());
				}
			}
		}catch(BizException e){
			fmlResponse.setStatus(new Status(e.getCode()));
		}catch(Exception e){
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		return fmlResponse;
	}
	
	
	@RequestMapping("/trigerDeviceOperator")
	@ResponseBody
	protected Response<TrigerDeviceRequest> doHandle(@RequestBody Request<TrigerDeviceRequest> request) throws BizException {
		Response<TrigerDeviceRequest> fmlResponse = new Response<TrigerDeviceRequest>(request);
		TrigerDeviceRequest trigerDeviceRequest = request.getData();
		try{
		Notification notification = new Notification();
		NotyTarget notyTarget = new NotyTarget();
		notyTarget.setUserId(request.getData().getUserId());
		Long deviceId = request.getData().getDeviceId();
		if(request.getData().getDeviceId() == null){
		deviceId = ejlUserDeviceServiceImpl.getUserUseingDeviceId(request.getData().getUserId());}
		notyTarget.setDeviceId(deviceId);
		notification.setTarget(notyTarget);
		NotyMessage message = new NotyMessage();
		message.setType(NotyMessage.Type.OPER);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		
		EjlDevice dbeEjlDevice = ejlDeviceServiceImpl.get(deviceId);
		if(dbeEjlDevice.getOnlineStatus().intValue()==0){
			fmlResponse.setStatus(new Status(StatusBizCode.USER_ISOFFLINE.getValue()));
			return fmlResponse;
		}
		if(trigerDeviceRequest.getType().intValue()==1){
			message.setCommand(20826);
			Map<String,String> data=new HashMap<String,String>();
			data.put("command", "20826");
			message.setData(data);
			notification.setMessage(message);
			notificationUtil.notification(notification);
		}else if(trigerDeviceRequest.getType().intValue()==2){
			EjlDevice ejlDevice = ejlDeviceServiceImpl.get(deviceId);
			message.setCommand(20827);
			Map<String,String> data=new HashMap<String,String>();
			data.put("command", "20827");
			data.put("continueTime", String.valueOf(trigerDeviceRequest.getContinueTime()));
			message.setData(data);
			notification.setMessage(message);
			data.put("status", trigerDeviceRequest.getStatus()+"");
			if(ejlDevice == null){
				fmlResponse.setStatus(new Status(StatusCode.PARAM_INVALID.getValue()));
				return fmlResponse;
			}
			if(trigerDeviceRequest.getStatus().intValue()==1){
				if(ejlDevice.getSleeplockStatus().intValue()==1){
					fmlResponse.setStatus(new Status(StatusBizCode.SLEEP_EXIST.getValue()));
					return fmlResponse;
				}
			}
			notificationUtil.notification(notification);
		}else if(trigerDeviceRequest.getType().intValue()==3){
			message.setCommand(20830);
			Map<String,String> data=new HashMap<String,String>();
			data.put("command", "20830");
			message.setData(data);
			notification.setMessage(message);
			notificationUtil.notification(notification);
		}
		}catch(Exception e){
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		return fmlResponse;
	}
}
