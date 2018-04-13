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
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.GetUserBaseInfoRequest;
import com.winterframework.efamily.dto.device.DeviceUserBaseInfo;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlComUserService;

@Controller("deviceUserBaseInfoController")
@RequestMapping("/device/userInfo")
public class DeviceUserBaseInfoController {

	private static final Logger logger = LoggerFactory.getLogger(DeviceUserBaseInfoController.class);
	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService; 
	
	@RequestMapping("/get")
	@ResponseBody
	public Response<DeviceUserBaseInfo> get(@RequestBody Request<GetUserBaseInfoRequest> request) throws Exception{
		DeviceUserBaseInfo info = new DeviceUserBaseInfo();
		Response<DeviceUserBaseInfo> fmlResponse = new Response<DeviceUserBaseInfo>(request);
		try{
			Long userId=request.getData().getUserId();
			EjlUser user = ejlComUserService.get(request.getData().getUserId());
			if(null==user){
				throw new BizException(StatusBizCode.DEVICE_USER_NOT_EXISTS.getValue(),new String[]{userId+""});
			}
			info.setHeadImage(user.getHeadImg());
			info.setNickName(user.getNickName());
			info.setPhoneNumber(user.getPhone());
			info.setSex(user.getSex()!= null?user.getSex().toString():null);
			info.setUserId(user.getId().toString());
			fmlResponse.setData(info);
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		}catch(Exception e){
			logger.error("get device user info error",e);
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		return fmlResponse;
	}
}
