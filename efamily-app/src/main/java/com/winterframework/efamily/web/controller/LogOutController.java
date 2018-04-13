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
import com.winterframework.efamily.dto.LogOutRequest;
import com.winterframework.efamily.dto.LogOutResponse;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 登陆handler
 * @author david
 *
 */
@Controller("logOutController")
@RequestMapping("/server")
public class LogOutController{

	private static final Logger log = LoggerFactory.getLogger(LogOutController.class); 
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/logOut")
	@ResponseBody
	protected Response<LogOutResponse> doHandle(@RequestBody Request<LogOutRequest> request) throws BizException {
		Response<LogOutResponse> fmlResponse = new Response<LogOutResponse>(request);
		LogOutResponse logOutResponse = new LogOutResponse();
		
		log.error("loginOutHandler");
		ejlUserServiceImpl.loginOut(request.getCtx(),request.getUserId(),request.getData().getToken());
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(logOutResponse);
		return fmlResponse;
	}
}
