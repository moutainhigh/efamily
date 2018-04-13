package com.winterframework.efamily.web.controller;

import java.util.List;

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
import com.winterframework.efamily.dto.AttentionDeviceStruc;
import com.winterframework.efamily.dto.GetAttentionDeviceRequest;
import com.winterframework.efamily.dto.GetAttentionDeviceResponse;
import com.winterframework.efamily.service.IEjlAttentionUserService;


@Controller("getAttentionDeviceController")
@RequestMapping("/server")
public class GetAttentionDeviceCottroller {
	private static final Logger log = LoggerFactory.getLogger(GetAttentionDeviceCottroller.class);
	@Resource(name = "ejlAttentionUserServiceImpl")
	private IEjlAttentionUserService ejlAttentionUserServiceImpl;

	@RequestMapping("/getAttentionDevice")
	@ResponseBody
	protected Response<GetAttentionDeviceResponse> doHandle(@RequestBody Request<GetAttentionDeviceRequest> request)throws BizException, JsonProcessingException {
		Response<GetAttentionDeviceResponse> fmlResponse = new Response<GetAttentionDeviceResponse>(request);
		GetAttentionDeviceResponse getAttentionDeviceResponse = new GetAttentionDeviceResponse();

		List<AttentionDeviceStruc> response = ejlAttentionUserServiceImpl.getAttentionDevice(request.getUserId());
		if(response != null){
   		    ObjectMapper mapper = new ObjectMapper();
		    getAttentionDeviceResponse.setDeviceList(mapper.writeValueAsString(response));
		    fmlResponse.setData(getAttentionDeviceResponse);
		}
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
