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

import com.winterframework.efamily.dto.OpenMonitorRequest;
import com.winterframework.efamily.dto.OpenMonitorResponse;
import com.winterframework.efamily.service.IEjlDeviceConfigService;


	/**
	 * 退出家庭handler
	 * @author floy
	 *
	 */
	@Controller("openMonitorController")
	@RequestMapping("/server")
	public class OpenMonitorController {
		private static final Logger logger = LoggerFactory.getLogger(OpenMonitorController.class);
		
		@Resource(name="deviceConfigServiceImpl")
		private IEjlDeviceConfigService deviceConfigServiceImpl;
		
		@RequestMapping("/openMonitor")
		@ResponseBody
		protected Response<OpenMonitorResponse> doHandle(@RequestBody Request<OpenMonitorRequest> request) throws Exception {
			Response<OpenMonitorResponse> fmlResponse = new Response<OpenMonitorResponse>(request);
			OpenMonitorResponse openMonitorResponse = new OpenMonitorResponse();
			Long userId = request.getData().getUserId();
			Long deviceId = request.getData().getDeviceId();
			String phoneNumber = request.getData().getPhoneNumber();
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			deviceConfigServiceImpl.openMonitorNotifyDevice(userId, deviceId, phoneNumber,request.getCtx());
			fmlResponse.setData(openMonitorResponse);
			return fmlResponse;
		}
	}
