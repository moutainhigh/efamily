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
import com.winterframework.efamily.dto.MessageStatusUpdateRequest;
import com.winterframework.efamily.dto.MessageStatusUpdateResponse;
import com.winterframework.efamily.service.IEjlMessageMarkService;

/**
 * 聊天handler
 * 
 * @author david
 * 
 */
@Controller("messageStatusUpdateController")
@RequestMapping("/server")
public class MessageStatusUpdateController{

	private static final Logger logger = LoggerFactory.getLogger(MessageStatusUpdateController.class);

	@Resource(name = "ejlMessageMarkServiceImpl")
	private IEjlMessageMarkService ejlMessageMarkServiceImpl;

	@RequestMapping("/messageStatusUpdate")
	@ResponseBody
	protected Response<MessageStatusUpdateResponse> doHandle(@RequestBody Request<MessageStatusUpdateRequest> request) throws BizException {
		Response<MessageStatusUpdateResponse> fmlResponse = new Response<MessageStatusUpdateResponse>(request);
		MessageStatusUpdateResponse messageStatusUpdateResponse = new MessageStatusUpdateResponse();
		
		Long sendUserId = Long.valueOf(request.getData().getSendUserId());
		Long receiveUserId = Long.valueOf(request.getUserId());
		Long chatRoomId = Long.valueOf(request.getData().getChatRoomId());
		Long chatType = Long.valueOf(request.getData().getChatType());
		Long messageId = Long.valueOf(request.getData().getMessageId());
		Long status = Long.valueOf(request.getData().getStatus());
		
		Long messageUpdateCount = ejlMessageMarkServiceImpl.updateMessageMarkStatus(request.getCtx(),messageId, sendUserId, receiveUserId, status, chatRoomId, chatType);
		messageStatusUpdateResponse.setMessageUpdateCount(messageUpdateCount);
		fmlResponse.setData(messageStatusUpdateResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
