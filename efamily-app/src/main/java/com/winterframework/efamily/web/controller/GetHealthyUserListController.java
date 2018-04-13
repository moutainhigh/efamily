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
import com.winterframework.efamily.dto.HealthlyUserRequest;
import com.winterframework.efamily.dto.HealthyUsersResponse;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.modules.spring.exetend.PropertyConfig;


/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("getHealthyUserListController")
@RequestMapping("/server")
public class GetHealthyUserListController {

	private static final Logger logger = LoggerFactory.getLogger(GetHealthyUserListController.class);
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	@PropertyConfig(value="server.url")
	private String serverUrl;
	
	@RequestMapping("/getHealthyUserList")
	@ResponseBody
	protected Response<HealthyUsersResponse> doHandle(@RequestBody Request<Long> request) throws BizException, JsonProcessingException {
	
		 
		Response<HealthyUsersResponse> fmlResponse = new Response<HealthyUsersResponse>(request);
		HealthyUsersResponse  response = new HealthyUsersResponse();
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		Long userId = request.getData();
		List<HealthlyUserRequest> list;
		list = ejlUserServiceImpl.getHealthlyUserByUserId(userId);
		response.setHealthyUserList(mapper.writeValueAsString(list));
		fmlResponse.setData(response);
		logger.info(mapper.writeValueAsString(list));
		return fmlResponse;
	}
}
