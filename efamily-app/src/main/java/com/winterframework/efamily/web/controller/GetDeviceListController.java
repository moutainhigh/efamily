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
import com.winterframework.efamily.dto.GetDeviceListRequest;
import com.winterframework.efamily.dto.GetDeviceListResponse;
import com.winterframework.efamily.dto.UserDeviceInfo;
import com.winterframework.efamily.service.IEjlFamilyUserService;

/**
 * 获取家庭成员列表handler
 * 
 * @author david
 * 
 */
@Controller("getDeviceListController")
@RequestMapping("/server")
public class GetDeviceListController {
	private static final Logger log = LoggerFactory
			.getLogger(GetDeviceListController.class);
	@Resource(name = "ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;

	@RequestMapping("/getDeviceList")
	@ResponseBody
	protected Response<GetDeviceListResponse> doHandle(
			@RequestBody Request<GetDeviceListRequest> request)
			throws BizException, JsonProcessingException {
		Response<GetDeviceListResponse> fmlResponse = new Response<GetDeviceListResponse>(
				request);
		GetDeviceListResponse getDeviceListResponse = new GetDeviceListResponse();

		List<UserDeviceInfo> response = ejlFamilyUserServiceImpl.getDeviceList(request.getUserId());
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		getDeviceListResponse.setDeviceList(mapper.writeValueAsString(response));
		fmlResponse.setData(getDeviceListResponse);
		return fmlResponse;
	}
}