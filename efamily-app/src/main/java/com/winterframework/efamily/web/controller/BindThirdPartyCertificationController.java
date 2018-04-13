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
import com.winterframework.efamily.dto.BindThirdPartyCertificationRequest;
import com.winterframework.efamily.dto.BindThirdPartyCertificationResponse;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 
 * @ClassName: BindThirdPartyCertificationController
 * @Description: TODO(绑定第三方登录)
 * @author jason
 * @date 2015-6-24 上午9:47:47
 * 
 */
@Controller("bindThirdPartyCertificationController")
@RequestMapping("/server")
public class BindThirdPartyCertificationController {
	private static final Logger logger = LoggerFactory
			.getLogger(BindThirdPartyCertificationController.class);
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@RequestMapping("/bindThirdPartyCertification")
	@ResponseBody
	protected Response<BindThirdPartyCertificationResponse> doHandle(@RequestBody Request<BindThirdPartyCertificationRequest> request)throws BizException {
		Response<BindThirdPartyCertificationResponse> fmlResponse = new Response<BindThirdPartyCertificationResponse>(request);
		Long userId = Long.valueOf(request.getData().getUserId());
		String sourceType = request.getData().getSourceType();
		String oprType = request.getData().getOprType();
		String sourceId = request.getData().getSourceId();
		// *** 新建聊天群 和 (群组用户关系)
		ejlUserServiceImpl.bindThirdPartyCertification(request.getCtx(), userId, sourceId,sourceType, oprType);
		fmlResponse.setData(new BindThirdPartyCertificationResponse());
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
