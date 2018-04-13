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
import com.winterframework.efamily.dto.GetThirdPartyLinkPhoneNumberRequest;
import com.winterframework.efamily.dto.GetThirdPartyLinkPhoneNumberResponse;
import com.winterframework.efamily.dto.GetVerifyCodeRequest;
import com.winterframework.efamily.dto.GetVerifyCodeResponse;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 获取第三方对应的手机号码
 * @author jason
 *
 */
@Controller("getThirdPartyLinkPhoneNumberController")
@RequestMapping("/server")
public class GetThirdPartyLinkPhoneNumberController{

	private static final Logger log = LoggerFactory.getLogger(GetThirdPartyLinkPhoneNumberController.class); 
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	
	@RequestMapping("/getThirdPartyLinkPhoneNumber")
	@ResponseBody
	protected Response<GetThirdPartyLinkPhoneNumberResponse> doHandle(@RequestBody Request<GetThirdPartyLinkPhoneNumberRequest> request) throws BizException {
		Response<GetThirdPartyLinkPhoneNumberResponse> fmlResponse = new Response<GetThirdPartyLinkPhoneNumberResponse>(request);
		GetThirdPartyLinkPhoneNumberResponse getThirdPartyLinkPhoneNumberResponse = new GetThirdPartyLinkPhoneNumberResponse();
		String sourceId =request.getData().getSourceId();
		Integer sourceType = request.getData().getSourceType();
		EjlUser user = ejlUserServiceImpl.getUserByThirdPartyToken(sourceId, sourceType);
		if(user!=null){
			getThirdPartyLinkPhoneNumberResponse.setPhoneNumber(user.getPhone());
			getThirdPartyLinkPhoneNumberResponse.setOprType("1");//已认证 根据号码获取IP
		}else{
			getThirdPartyLinkPhoneNumberResponse.setPhoneNumber("");
			getThirdPartyLinkPhoneNumberResponse.setOprType("2");//未认证
		}
		
		fmlResponse.setData(getThirdPartyLinkPhoneNumberResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
