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
import com.winterframework.efamily.dto.GetUserBarrierRequest;
import com.winterframework.efamily.dto.GetUserBarrierResponse;
import com.winterframework.efamily.entity.EjlUserBarrier;
import com.winterframework.efamily.service.IEjlUserBarrierService;

	@Controller("getUserBarrierController")
	@RequestMapping("/server")
	public class GetUserBarrierController {
		private static final Logger log = LoggerFactory.getLogger(GetUserBarrierController.class);
		@Resource(name = "ejlUserBarrierServiceImpl")
		private IEjlUserBarrierService ejlUserBarrierServiceImpl;

		@RequestMapping("/getUserBarrier")
		@ResponseBody
		protected Response<GetUserBarrierResponse> doHandle(
				@RequestBody Request<GetUserBarrierRequest> request)
				throws BizException {
			Response<GetUserBarrierResponse> fmlResponse = new Response<GetUserBarrierResponse>(request);
			GetUserBarrierResponse getDeviceListResponse = new GetUserBarrierResponse();

			EjlUserBarrier response = ejlUserBarrierServiceImpl.getEjlUserBarrier(request.data.getUserId());
			if(response!=null){
				getDeviceListResponse.setStatus(response.getStatus());
				getDeviceListResponse.setRadius(response.getRadius());
				getDeviceListResponse.setX(response.getLocation().split(",")[0]);
				getDeviceListResponse.setY(response.getLocation().split(",")[1]);
				getDeviceListResponse.setType(response.getType());
				getDeviceListResponse.setRemark(response.getRemark());
			}else{
				getDeviceListResponse.setStatus(0);
				getDeviceListResponse.setRadius(0L);
				getDeviceListResponse.setX("0");
				getDeviceListResponse.setY("0");
				getDeviceListResponse.setType(1);
				getDeviceListResponse.setRemark("");
			}
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(getDeviceListResponse);
			return fmlResponse;
		}
	}