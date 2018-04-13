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
import com.winterframework.efamily.dto.AttentionUserStruc;
import com.winterframework.efamily.dto.GetAttentionUserRequest;
import com.winterframework.efamily.dto.GetAttentionUserResponse;
import com.winterframework.efamily.service.IEjlAttentionUserService;
import com.winterframework.efamily.service.IEjlUserDeviceService;


@Controller("getAttentionUserController")
@RequestMapping("/server")
public class GetAttentionUserCottroller {
	private static final Logger logger = LoggerFactory.getLogger(GetAttentionUserCottroller.class);
	@Resource(name = "ejlAttentionUserServiceImpl")
	private IEjlAttentionUserService ejlAttentionUserServiceImpl;

	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;
	
	@RequestMapping("/getAttentionUser")
	@ResponseBody
	protected Response<GetAttentionUserResponse> doHandle(@RequestBody Request<GetAttentionUserRequest> request)throws BizException, JsonProcessingException {
		Response<GetAttentionUserResponse> fmlResponse = new Response<GetAttentionUserResponse>(request);
		GetAttentionUserResponse getAttentionUserResponse = new GetAttentionUserResponse();
        Long deviceId = request.getData().getDeviceId();
        Long userId = ejlUserDeviceServiceImpl.getDeviceUseingUserId(deviceId);
        if(userId != null){
    		List<AttentionUserStruc> response = ejlAttentionUserServiceImpl.getAttentionUser(userId);
    		if(response != null){
       		    ObjectMapper mapper = new ObjectMapper();
    		    getAttentionUserResponse.setUserList(mapper.writeValueAsString(response));
    		    fmlResponse.setData(getAttentionUserResponse);
    		}else{
    			logger.warn("获取数据为空：deviceId = "+deviceId);
    		}
        }else{
        	logger.warn("获取数据为空：deviceId = "+deviceId);
        }
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
