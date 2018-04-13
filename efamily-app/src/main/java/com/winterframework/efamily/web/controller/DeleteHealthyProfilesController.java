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
import com.winterframework.efamily.dto.DeleteHealthyProfilesRequest;
import com.winterframework.efamily.dto.DeleteHealthyProfilesResponse;
import com.winterframework.efamily.service.IEjlHealthManageService;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("deleteHealthyProfilesController")
@RequestMapping("/server")
public class DeleteHealthyProfilesController  {

	@Resource(name = "ejlHealthManageServiceImpl")
	private IEjlHealthManageService ejlHealthManageServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(DeleteHealthyProfilesController.class);

	@RequestMapping("/deleteHealthyProfiles")
	@ResponseBody
	protected Response<DeleteHealthyProfilesResponse> doHandle(@RequestBody Request<DeleteHealthyProfilesRequest> request) throws BizException {
		Response<DeleteHealthyProfilesResponse> fmlResponse = new Response<DeleteHealthyProfilesResponse>(request);
		DeleteHealthyProfilesResponse deleteHealthyProfilesResponse = new DeleteHealthyProfilesResponse();
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ejlHealthManageServiceImpl.deleteHealthyProfileByUserId(null, request.getUserId(),
				request.getDeviceId() == null ? null : request.getDeviceId()); 
		fmlResponse.setData(deleteHealthyProfilesResponse);
		return fmlResponse;
	}
}
