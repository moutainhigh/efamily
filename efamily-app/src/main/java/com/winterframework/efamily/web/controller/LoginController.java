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
import com.winterframework.efamily.dto.LoginRequest;
import com.winterframework.efamily.dto.LoginResponse;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 登陆handler
 * @author david
 *
 */
@Controller("loginController")
@RequestMapping("/server")
public class LoginController{

	private static final Logger log = LoggerFactory.getLogger(LoginController.class); 
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/login")
	@ResponseBody
	protected Response<LoginResponse> doHandle(@RequestBody Request<LoginRequest> request) throws BizException {
		Response<LoginResponse> fmlResponse = new Response<LoginResponse>(request);
		LoginResponse loginResponse = new LoginResponse();
		
		LoginResponse response=ejlUserServiceImpl.login(request.getCtx(),request.getData());
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		//loginResponse.setToken(getToken(response.getUserId()));
		loginResponse.setUserId(response.getUserId());
		loginResponse.setFamilyId(response.getFamilyId());
		loginResponse.setNickName(response.getNickName());
		loginResponse.setHeadImgUrl(response.getHeadImgUrl());
		loginResponse.setFamilyCode(response.getFamilyCode());
		loginResponse.setFamilyName(response.getFamilyName());
		loginResponse.setSignature(response.getSignature());
		loginResponse.setSex(response.getSex());
		loginResponse.setUserType(response.getUserType());
		loginResponse.setFamilyHostUserId(response.getFamilyHostUserId());
		loginResponse.setSource(response.getSource());
		fmlResponse.setData(loginResponse);
		return fmlResponse;
	}
	/*private String getToken(Long userId){  
		return TokenManager.save(userId);
	}*/
}
