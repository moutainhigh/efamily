/**   
* @Title: SetStepGoalHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-23 下午3:31:11 
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

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.SetStepGoalRequest;
import com.winterframework.efamily.dto.SetStepGoalResponse;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlUserService;

/** 
* @ClassName: SetStepGoalHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-23 下午3:31:11 
*  
*/
@Controller("setStepGoalController")
@RequestMapping("/server")
public class SetStepGoalController {

	private static final Logger log = LoggerFactory.getLogger(SetStepGoalController.class); 
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param request
	* @return 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(com.winterframework.efamily.server.core.ServerContext, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@RequestMapping("/setStepGoal")
	@ResponseBody
	protected Response<SetStepGoalResponse> doHandle(@RequestBody Request<SetStepGoalRequest> request) throws BizException {
		Response<SetStepGoalResponse> fmlResponse = new Response<SetStepGoalResponse>(request);
		SetStepGoalResponse setStepGoalResponse = new SetStepGoalResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		if(request.getData().getType().equals("1")){
			/*EjlUser user = new EjlUser();
			user.setId(request.getData().getUserId());
			user.setStepGoal(request.getData().getTargetStep());
			ejlUserServiceImpl.saveOrUpdateUser(request.getCtx(),user);*/
			fmlResponse.setData(setStepGoalResponse);
		}
		return fmlResponse;
	}

}
