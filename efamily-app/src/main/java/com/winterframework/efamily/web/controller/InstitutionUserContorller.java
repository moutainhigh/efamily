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
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.UserInfoReq;
import com.winterframework.efamily.service.IEfComOrgDeviceService;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.service.IInstitutionUserService;
@Controller("institutionUserContorller")
@RequestMapping("/server")
public class InstitutionUserContorller {

	private static final Logger log = LoggerFactory.getLogger(InstitutionUserContorller.class);
	@Resource(name = "institutionUserServiceImpl")
	private IInstitutionUserService institutionUserServiceImpl;
	
	@Resource(name = "efComOrgDeviceServiceImpl")
	private IEfComOrgDeviceService efComOrgDeviceServiceImpl;
	
	@Resource(name = "efComOrgServiceImpl")
	private IEfComOrgService efComOrgServiceImpl;
	

	@RequestMapping("/userInfo")
	@ResponseBody
	protected Response<Object> userInfo(@RequestBody Request<List<UserInfoReq>> request)throws BizException{
		Response<Object> fmlResponse = new Response<Object>(request);
		try{
			List<UserInfoReq> list = (List<UserInfoReq>)request.getData();
			String key = (String)request.getCtx().get("orgKey");
			institutionUserServiceImpl.institutionUserManage(request.getCtx(), list, key);
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		}catch(BizException e){
			log.error("userInfo error", e);
			fmlResponse.setStatus(new Status(e.getCode()));
		}catch(Exception e){
			log.error("userInfo error", e);
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		return fmlResponse;
	}
}
