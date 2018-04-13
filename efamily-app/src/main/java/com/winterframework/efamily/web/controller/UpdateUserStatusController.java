package com.winterframework.efamily.web.controller;

import java.util.Date;

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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.UpdateUserStatusRequest;
import com.winterframework.efamily.dto.UpdateUserStatusResponse;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 修改用户状态
 *
 */
@Controller("updateUserStatusController")
@RequestMapping("/server")
public class UpdateUserStatusController  {
	private static final Logger logger = LoggerFactory.getLogger(UpdateUserStatusController.class);

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@RequestMapping("/updateUserStatus")
	@ResponseBody
	protected Response<UpdateUserStatusResponse> doHandle(@RequestBody Request<UpdateUserStatusRequest> request) throws BizException {
		Response<UpdateUserStatusResponse> fmlResponse = new Response<UpdateUserStatusResponse>(request);
		UpdateUserStatusResponse UpdateUserStatusResponse = new UpdateUserStatusResponse();
		 
		EjlUser user = ejlUserServiceImpl.get(request.getUserId());
		if(null==null){
			throw new BizException(StatusBizCode.USER_UN_VALID.getValue());
		}
		if(EfamilyConstant.USER_STATUS_ONLINE == Long.valueOf(request.getData().getStatus())){
			user.setLastLoginTime(new Date());
			user.setStatus(Long.valueOf(EfamilyConstant.USER_STATUS_ONLINE));
		}else{
			user.setStatus(Long.valueOf(EfamilyConstant.USER_STATUS_OFFLINE));
		}
		ejlUserServiceImpl.save(request.getCtx(),user);
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(UpdateUserStatusResponse);
		return fmlResponse;
	}
}
