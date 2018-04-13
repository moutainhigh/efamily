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
import com.winterframework.efamily.dto.GetUserHealthlyConfigRequest;

import com.winterframework.efamily.dto.UserHealthlyConfigStruc;
import com.winterframework.efamily.service.IEjlUserService;



/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("getHealthyUserConfigController")
@RequestMapping("/server")
public class GetHealthyUserConfigController {

	private static final Logger logger = LoggerFactory.getLogger(GetHealthyUserConfigController.class);
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/getHealthyUserConfig")
	@ResponseBody
	protected Response<UserHealthlyConfigStruc> doHandle(@RequestBody Request<GetUserHealthlyConfigRequest> request) throws BizException {
		Response<UserHealthlyConfigStruc> fmlResponse = new Response<UserHealthlyConfigStruc>(request);
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		
		UserHealthlyConfigStruc struc = ejlUserServiceImpl.getHealthlyUserConfig(request.getData().getUserId(), request.getData().getDeviceId());
		fmlResponse.setData(struc);
		return fmlResponse;
	}
}
