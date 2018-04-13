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
import com.winterframework.efamily.dto.GetFamilyAttentionRequest;
import com.winterframework.efamily.dto.GetFamilyAttentionResponse;
import com.winterframework.efamily.service.IEjlFamilyService;


	@Controller("getFamilyAttentionDetailController")
	@RequestMapping("/server")
	public class GetFamilyAttentionDetailController{
		private static final Logger logger = LoggerFactory.getLogger(GetFamilyAttentionDetailController.class); 
		
		@Resource(name="ejlFamilyServiceImpl")
		private IEjlFamilyService ejlFamilyServiceImpl;
		

		@RequestMapping("/getFamilyAttentionDetail")
		@ResponseBody
		protected Response<GetFamilyAttentionResponse> doHandle(@RequestBody Request<GetFamilyAttentionRequest> request) throws BizException {
			Response<GetFamilyAttentionResponse> fmlResponse = new Response<GetFamilyAttentionResponse>(request);
			GetFamilyAttentionResponse createChatGroupResponse = new GetFamilyAttentionResponse();
			Long familyId = request.getData().getFamilyId();
			if(familyId != null){
				createChatGroupResponse = ejlFamilyServiceImpl.getFamilyAttentionDetail(request.getUserId(),Long.valueOf(familyId));
				fmlResponse.setData(createChatGroupResponse);
				fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			}else{
				fmlResponse.setStatus(new Status(StatusCode.FAILED.getValue()));
				logger.info("获取家庭关注列表信息时,参数familyId为空,获取失败。");
			}
			return fmlResponse;
		}
	}
