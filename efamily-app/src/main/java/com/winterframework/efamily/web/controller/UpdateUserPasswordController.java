package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.UpdateUserPasswordRequest;
import com.winterframework.efamily.dto.UpdateUserPasswordResponse;
import com.winterframework.efamily.service.IEjlUserService;

/** 
* @ClassName: UpdateUserPasswordHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author jason
* @date 2015-7-2 下午5:48:59 
*  
*/
@Controller("updateUserPasswordController")
@RequestMapping("/server")
public class UpdateUserPasswordController {

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param req
	* @return 
	 * @throws Exception 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(com.winterframework.efamily.server.core.ServerContext, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@RequestMapping("/updateUserPassword")
	@ResponseBody
	protected Response<UpdateUserPasswordResponse> doHandle(@RequestBody Request<UpdateUserPasswordRequest> request) throws Exception {
		Response<UpdateUserPasswordResponse> fmlResponse = new Response<UpdateUserPasswordResponse>(request);
		UpdateUserPasswordResponse updateUserPasswordResponse = new UpdateUserPasswordResponse();
		String phoneNumber = request.getData().getPhoneNumber();
		String password = request.getData().getPassword();
		String verifyCode = request.getData().getVerifyCode();
		Integer oprType = request.getData().getOprType();
		String oldPassword = request.getData().getOldPassword();
		
		if(ejlUserServiceImpl.modifyPassword(request.getCtx(), phoneNumber, verifyCode, password, oprType, oldPassword)){
			updateUserPasswordResponse.setStatus(StatusCode.OK.getValue());
		}
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(updateUserPasswordResponse);
		return fmlResponse;
	}

}

