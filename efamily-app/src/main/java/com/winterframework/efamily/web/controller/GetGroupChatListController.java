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
import com.winterframework.efamily.dto.ChatGroupListResponse;
import com.winterframework.efamily.dto.GetGroupChatListRequest;
import com.winterframework.efamily.dto.GetGroupChatListResponse;
import com.winterframework.efamily.service.IEjlUserService;

@Controller("getGroupChatListController")
@RequestMapping("/server")
public class GetGroupChatListController{
	private static final Logger log = LoggerFactory.getLogger(GetGroupChatListController.class); 
	
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/getGroupChatList")
	@ResponseBody
	protected Response<GetGroupChatListResponse> doHandle(@RequestBody Request<GetGroupChatListRequest> request) throws BizException, JsonProcessingException {
		Response<GetGroupChatListResponse> fmlResponse = new Response<GetGroupChatListResponse>(request);
		GetGroupChatListResponse getGroupChatListResponse = new GetGroupChatListResponse();
		
		List<ChatGroupListResponse> response =  ejlUserServiceImpl.getGroupChatListByUserId(request.getUserId());
		ObjectMapper mapper=new ObjectMapper();
		getGroupChatListResponse.setGroupchatlist(mapper.writeValueAsString(response));
		fmlResponse.setData(getGroupChatListResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
