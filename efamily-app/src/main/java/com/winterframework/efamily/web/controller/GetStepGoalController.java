/**   
* @Title: GetStepGoalHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-23 下午5:30:18 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlComUserExtendInfoDao;
import com.winterframework.efamily.dto.GetStepGoalRequest;
import com.winterframework.efamily.dto.GetStepGoalResponse;
import com.winterframework.efamily.dto.StepGoalStruc;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserExtendInfo;
import com.winterframework.efamily.service.IEjlHealthManageService;
import com.winterframework.efamily.service.IEjlUserService;

/** 
* @ClassName: GetStepGoalHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-23 下午5:30:18 
*  
*/
@Controller("getStepGoalController")
@RequestMapping("/server")
public class GetStepGoalController {

	private static final Logger logger = LoggerFactory.getLogger(GetStepGoalController.class);
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@Resource(name = "ejlHealthManageServiceImpl")
	private IEjlHealthManageService ejlHealthManageServiceImpl;
	
	@Resource(name="ejlComUserExtendInfoDaoImpl")
	private IEjlComUserExtendInfoDao ejlComUserExtendInfoDaoImpl;

	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param request
	* @return 
	 * @throws JsonProcessingException 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(com.winterframework.efamily.server.core.ServerContext, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@RequestMapping("/getStepGoal")
	@ResponseBody
	protected Response<GetStepGoalResponse> doHandle(@RequestBody Request<GetStepGoalRequest> request) throws BizException, JsonProcessingException {
		Response<GetStepGoalResponse> fmlResponse = new Response<GetStepGoalResponse>(request);
		GetStepGoalResponse getStepGoalResponse = new GetStepGoalResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper=new ObjectMapper();
		if (request.getData().getType().equals("1")) {
			StepGoalStruc struc = new StepGoalStruc();
			struc.setId(request.getData().getUserId());
			EjlUserExtendInfo entity = new EjlUserExtendInfo();
			entity.setUserId(request.getData().getUserId());
			EjlUserExtendInfo ejlUserExtendInfo = ejlComUserExtendInfoDaoImpl.selectOneObjByAttribute(entity);
			struc.setTargetStep(ejlUserExtendInfo.getStepGoal() == null ? 0 : ejlUserExtendInfo.getStepGoal());
			Long currentStep = ejlHealthManageServiceImpl.getAllDayStepsByUser(request.getData().getUserId(),
					DateUtils.convertLong2Date(request.getData().getQueryDate()));
			struc.setCurrentStep(currentStep == null ? 0 : currentStep);
			
			getStepGoalResponse.setTargetStep(mapper.writeValueAsString(struc));
			fmlResponse.setData(getStepGoalResponse);
		}
		return fmlResponse;
	}

}
