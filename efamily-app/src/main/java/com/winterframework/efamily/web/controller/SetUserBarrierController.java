package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.winterframework.efamily.dto.SetUserBarrierRequest;
import com.winterframework.efamily.dto.SetUserBarrierResponse;
import com.winterframework.efamily.service.IEjlUserBarrierService;

	@Controller("setUserBarrierController")
	@RequestMapping("/server")
	public class SetUserBarrierController {
		private static final Logger log = LoggerFactory.getLogger(SetUserBarrierController.class);
		@Resource(name = "ejlUserBarrierServiceImpl")
		private IEjlUserBarrierService ejlUserBarrierServiceImpl;

		@RequestMapping("/setUserBarrier")
		@ResponseBody
		protected Response<SetUserBarrierResponse> doHandle(
				@RequestBody Request<SetUserBarrierRequest> request)
				throws BizException {
			Response<SetUserBarrierResponse> fmlResponse = new Response<SetUserBarrierResponse>(request);
			SetUserBarrierResponse getDeviceListResponse = new SetUserBarrierResponse();
			Long userId = request.getData().getUserId();
			Long fenceId = request.getData().getFenceId();
			Integer status = request.getData().getStatus();
			String location = request.getData().getX()+","+request.getData().getY();
			Long radius = StringUtils.isNotEmpty(request.getData().getRadius())? Long.valueOf(request.getData().getRadius()):0L ;
			Integer type = request.getData().getType();
			String remark = StringUtils.isNotEmpty(request.getData().getRemark())?request.getData().getRemark():"";
            //********** 检测设置围栏 权限   *******			
			if(ejlUserBarrierServiceImpl.checkSetEjlUserBarrier(request.getUserId(),userId)){
				ejlUserBarrierServiceImpl.setEjlUserBarrier(request.getCtx(),fenceId,userId, status, location, radius,type,remark,request.getData().getOrgTag());
			}
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(getDeviceListResponse);
			return fmlResponse;
		}
	}
