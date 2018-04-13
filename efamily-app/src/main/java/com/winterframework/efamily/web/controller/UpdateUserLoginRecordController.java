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
import com.winterframework.efamily.dto.UpdateUserLoginRecordRequest;
import com.winterframework.efamily.dto.UpdateUserLoginRecordResponse;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 修改用户状态
 * 
 */
@Controller("updateUserLoginRecordController")
@RequestMapping("/server")
public class UpdateUserLoginRecordController {
	private static final Logger logger = LoggerFactory
			.getLogger(UpdateUserLoginRecordController.class);

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@RequestMapping("/updateUserLoginRecord")
	@ResponseBody
	protected Response<UpdateUserLoginRecordResponse> doHandle(
			@RequestBody Request<UpdateUserLoginRecordRequest> request)
			throws BizException {
		Response<UpdateUserLoginRecordResponse> fmlResponse = new Response<UpdateUserLoginRecordResponse>(
				request);
		UpdateUserLoginRecordResponse UpdateUserLoginRecordResponse = new UpdateUserLoginRecordResponse();

        String token = request.getData().getToken();
        String remark = request.getData().getRemark();
        Integer status = request.getData().getStatus();
		ejlUserServiceImpl.loginRecord(request.getCtx(), request.getUserId(), token, remark, status);

		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(UpdateUserLoginRecordResponse);
		return fmlResponse;
	}
}
