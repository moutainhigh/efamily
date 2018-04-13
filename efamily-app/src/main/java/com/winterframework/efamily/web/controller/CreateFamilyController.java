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
import com.winterframework.efamily.dto.CreateFamilyRequest;
import com.winterframework.efamily.dto.CreateFamilyResponse;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 创建家庭
 * @author david
 *
 */
@Controller("createFamilyController")
@RequestMapping("/server")
public class CreateFamilyController {
	private static final Logger logger = LoggerFactory.getLogger(CreateFamilyController.class);
	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/createFamily")
	@ResponseBody
	protected Response<CreateFamilyResponse> doHandle(@RequestBody Request<CreateFamilyRequest> request) throws BizException {
		Response<CreateFamilyResponse> fmlResponse = new Response<CreateFamilyResponse>(request);
		//*** 检查用户是否已经有家庭
		Long familyCount = ejlUserServiceImpl.getFamilyCountByUserId(request.getUserId());
		if(familyCount == 0){
			CreateFamilyResponse createFamilyResponse = new CreateFamilyResponse();
			//*** 新建家庭  和 (家庭用户关系) 
			CreateFamilyResponse response=ejlFamilyServiceImpl.createFamily(request.getCtx(),request.getUserId(), request.getData().getFamilyName());
			createFamilyResponse.setFamilyId(response.getFamilyId());
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(createFamilyResponse);
		}else{
			fmlResponse.setStatus(new Status(StatusCode.FAILED.getValue()));
			logger.warn("创建家庭，创建者已经在家庭中，不允许再创建家庭。userId = "+request.getUserId());
		}
		return fmlResponse;
	}
}