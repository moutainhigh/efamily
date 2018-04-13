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
import com.winterframework.efamily.dto.GetVerifyCodeRequest;
import com.winterframework.efamily.dto.GetVerifyCodeResponse;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 登陆handler
 * @author david
 *
 */
@Controller("getVerifyCodeController")
@RequestMapping("/server")
public class GetVerifyCodeController{

	private static final Logger log = LoggerFactory.getLogger(GetVerifyCodeController.class); 
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	
	@RequestMapping("/getVerifyCode")
	@ResponseBody
	protected Response<GetVerifyCodeResponse> doHandle(@RequestBody Request<GetVerifyCodeRequest> request) throws BizException {
		Response<GetVerifyCodeResponse> fmlResponse = new Response<GetVerifyCodeResponse>(request);
		GetVerifyCodeResponse getVerifyCodeResponse = new GetVerifyCodeResponse();
		try{
			String ip = request.getData().getIp();
			String phoneNumber = request.getData().getPhoneNumber();
			Integer appType = request.getData().getAppType();
			if(ejlUserServiceImpl.checkVerifyCode(request.getCtx(), ip, phoneNumber, appType)){
				Integer code=ejlUserServiceImpl.getVerifyCode(request.getCtx(),phoneNumber,appType);
				if(code!=null && code.intValue()!=-1){
					fmlResponse.setData(getVerifyCodeResponse);
					fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
				}else{
					fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
				}
			}
		}catch(BizException e){
			log.error("获取验证码异常：phonenumber = "+request.getData().getPhoneNumber()+" ; code = "+e.getCode()+" ; ");
			fmlResponse.setStatus(new Status(e.getCode()));
		}catch (Exception e) {
			log.error("获取验证码未知异常：phonenumber = "+request.getData().getPhoneNumber());
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}

		return fmlResponse;
	}
}
