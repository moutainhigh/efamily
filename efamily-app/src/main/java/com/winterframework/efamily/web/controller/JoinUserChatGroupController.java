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
import com.winterframework.efamily.dto.JoinUserChatGroupRequest;
import com.winterframework.efamily.dto.JoinUserChatGroupResponse;
import com.winterframework.efamily.service.IEjlUserService;


@Controller("joinUserChatGroupController")
@RequestMapping("/server")
public class JoinUserChatGroupController{
	private static final Logger logger = LoggerFactory.getLogger(JoinUserChatGroupController.class); 
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	

	@RequestMapping("/joinUserChatGroup")
	@ResponseBody
	protected Response<JoinUserChatGroupResponse> doHandle(@RequestBody Request<JoinUserChatGroupRequest> request) throws BizException {
		Response<JoinUserChatGroupResponse> fmlResponse = new Response<JoinUserChatGroupResponse>(request);
		JoinUserChatGroupResponse joinUserChatGroupResponse = new JoinUserChatGroupResponse();
		
		//*** 邀请别人加入  或者 自己加入 ，一个人或者多人  用户 逗号隔开   
		String userIds = request.getData().getUserIds();
		Long chatRoomId = request.getData().getChatRoomId();
		
		ejlUserServiceImpl.joinUserChatGroupInfo(request.getCtx(),userIds, chatRoomId);
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(joinUserChatGroupResponse);
		return fmlResponse;
	}
}