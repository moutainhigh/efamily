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
import com.winterframework.efamily.dto.MessageCatchRequest;
import com.winterframework.efamily.dto.MessageCatchResponse;
import com.winterframework.efamily.dto.MessageNoReadInfoResponse;
import com.winterframework.efamily.entity.EjlMessageMark;
import com.winterframework.efamily.service.IEjlMessageMarkService;

/**
 * 
 * 拉取聊天消息记录接口
 * 
 * @author david
 * 
 */
@Controller("messageCatchController")
@RequestMapping("/server")
public class MessageCatchController{

	private static final Logger logger = LoggerFactory.getLogger(MessageCatchController.class);

	@Resource(name = "ejlMessageMarkServiceImpl")
	private IEjlMessageMarkService ejlMessageMarkServiceImpl;

	@RequestMapping("/messageCatch")
	@ResponseBody
	protected Response<MessageCatchResponse> doHandle(@RequestBody Request<MessageCatchRequest> request) throws BizException, JsonProcessingException {
		Response<MessageCatchResponse> fmlResponse = new Response<MessageCatchResponse>(request);
		MessageCatchResponse messageCatchResponse = new MessageCatchResponse();
		
		Long sendUserId = Long.valueOf(request.getData().getSendUserId());
		Long receiveUserId = Long.valueOf(request.getUserId());
		Long chatRoomId = Long.valueOf(request.getData().getChatRoomId());
		Long chatType = Long.valueOf(request.getData().getChatType());
		Long page = Long.valueOf(request.getData().getPage());
		Long pageSize = Long.valueOf(request.getData().getPageSize());
		
		page=page==0?Long.MAX_VALUE:page+1;
		List<MessageNoReadInfoResponse> ejlMessageMarkList = ejlMessageMarkServiceImpl.getAllMessageByChatObj(request.getCtx(),sendUserId, receiveUserId, chatRoomId, chatType, page, pageSize);
		if(ejlMessageMarkList!=null && ejlMessageMarkList.size()>0){
			for(int i=0;i<ejlMessageMarkList.size()-1;i++){
				MessageNoReadInfoResponse ejlMessageMarkCurrent = ejlMessageMarkList.get(i);
				MessageNoReadInfoResponse ejlMessageMarkPrevious = ejlMessageMarkList.get(i+1);
				ejlMessageMarkCurrent.setPreMessageId(ejlMessageMarkPrevious.getMessageId());
			}
			if(ejlMessageMarkList.size()>pageSize){
				ejlMessageMarkList.remove(ejlMessageMarkList.size()-1);
			}
		}
		ObjectMapper mapper=new ObjectMapper();
		messageCatchResponse.setMessageList(mapper.writeValueAsString(ejlMessageMarkList));
		fmlResponse.setData(messageCatchResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
