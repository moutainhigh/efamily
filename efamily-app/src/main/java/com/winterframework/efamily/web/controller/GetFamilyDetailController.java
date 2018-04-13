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
import com.winterframework.efamily.dto.GetFamilyDetailRequest;
import com.winterframework.efamily.dto.GetFamilyDetailResponse;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlFamilyUserService;


@Controller("getFamilyDetailController")
@RequestMapping("/server")
public class GetFamilyDetailController{
	private static final Logger logger = LoggerFactory.getLogger(GetFamilyDetailController.class); 
	
	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;

	@Resource(name="ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@RequestMapping("/getFamilyDetail")
	@ResponseBody
	protected Response<GetFamilyDetailResponse> doHandle(@RequestBody Request<GetFamilyDetailRequest> request) throws BizException {
		Response<GetFamilyDetailResponse> fmlResponse = new Response<GetFamilyDetailResponse>(request);
		GetFamilyDetailResponse createChatGroupResponse = new GetFamilyDetailResponse();
		
		Long familyId = request.getData().getFamilyId();
		if(familyId != null){
			EjlFamily family = ejlFamilyServiceImpl.get(Long.valueOf(familyId));
			if(family != null){
				createChatGroupResponse.setFamilyCode(family.getCode());
				createChatGroupResponse.setFamilyName(family.getName());
				createChatGroupResponse.setHeadImgUrl(family.getHeadImg());
				Long familyHostUserId = ejlFamilyUserServiceImpl.getHostByFamilyId(family.getId());
				createChatGroupResponse.setFamilyHostUserId(familyHostUserId);
				fmlResponse.setData(createChatGroupResponse);
				fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			}else{
				fmlResponse.setStatus(new Status(StatusCode.FAILED.getValue()));
				logger.info("获取家庭信息时,家庭信息为空,获取失败。");
			}
		}else{
			fmlResponse.setStatus(new Status(StatusCode.FAILED.getValue()));
			logger.info("获取家庭信息时,参数familyId为空,获取失败。");
		}
		return fmlResponse;
	}
}