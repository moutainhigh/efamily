package com.winterframework.efamily.web.controller;

import java.util.List;

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
import com.winterframework.efamily.dto.MessageCatchRequest;
import com.winterframework.efamily.dto.MessageLastRequest;
import com.winterframework.efamily.dto.MessageLastResponse;
import com.winterframework.efamily.dto.MessageNoReadInfoResponse;
import com.winterframework.efamily.service.IEjlMessageMarkService;

/**
 * 
 * 拉取聊天消息记录接口
 * 
 * @author david
 * 
 */
@Controller("messageLastController")
@RequestMapping("/server")
public class MessageLastController{

	private static final Logger logger = LoggerFactory.getLogger(MessageLastController.class);

	@Resource(name = "ejlMessageMarkServiceImpl")
	private IEjlMessageMarkService ejlMessageMarkServiceImpl;

	@RequestMapping("/messageLast")
	@ResponseBody
	protected Response<MessageLastResponse> doHandle(@RequestBody Request<MessageLastRequest> request) throws BizException {
		Response<MessageLastResponse> fmlResponse = new Response<MessageLastResponse>(request);
		MessageLastResponse messageLastResponse = new MessageLastResponse();
		
		Long sendUserId = Long.valueOf(request.getData().getSendUserId());
		Long receiveUserId = Long.valueOf(request.getUserId());
		Long chatRoomId = Long.valueOf(request.getData().getChatRoomId());
		Long chatType = Long.valueOf(request.getData().getChatType());
		Long lastReadedMessageId = Long.valueOf(request.getData().getLastReadedMessageId());
		Long messageLastId = 0L;
		Long noReadMessageSize  = 0L;
		List<MessageNoReadInfoResponse> ejlMessageMarkList = ejlMessageMarkServiceImpl.getAllMessageByChatObj(request.getCtx(),sendUserId, receiveUserId, chatRoomId, chatType, Long.MAX_VALUE, 0L);
		if(ejlMessageMarkList!=null && ejlMessageMarkList.size()>0){
			messageLastId = ejlMessageMarkList.get(0).getMessageId();
			if(messageLastId.longValue()>lastReadedMessageId.longValue()){
				noReadMessageSize = ejlMessageMarkServiceImpl.getTowMessageIdInternalCount(request.getCtx(),lastReadedMessageId, sendUserId, receiveUserId, chatRoomId, chatType);
			}
		}
		messageLastResponse.setNoReadMessageSize(noReadMessageSize);
		messageLastResponse.setMessageLastId(messageLastId.longValue()+"");
		fmlResponse.setData(messageLastResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
