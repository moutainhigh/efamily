package com.winterframework.efamily.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.aop.annotation.RequestCxt;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.SendChatMessageRequest;
import com.winterframework.efamily.dto.SendChatMessageResponse;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.service.IEjlMessageService;

/**
 * 聊天handler
 * 
 * @author david
 * 
 */
@Controller("sendChatMessageController")
@RequestMapping("/server")
public class SendChatMessageController{

	private static final Logger logger = LoggerFactory.getLogger(SendChatMessageController.class);

	@Resource(name = "ejlMessageServiceImpl")
	private IEjlMessageService ejlMessageServiceImpl;

	@RequestMapping("/sendChatMessage")
	@ResponseBody
	public Response<SendChatMessageResponse> doHandle(@RequestBody Request<SendChatMessageRequest> request) throws BizException{
		Response<SendChatMessageResponse> fmlResponse = new Response<SendChatMessageResponse>(request);
		SendChatMessageResponse sendChatMessageResponse = new SendChatMessageResponse();
		logger.error("AAAAAAAAAAAAAAAAAAAAAAAA userId="+request.getUserId()+" thread="+Thread.currentThread()+" id="+Thread.currentThread().getId()+" name="+Thread.currentThread().getName()+" group="+Thread.currentThread().getThreadGroup());
		Long contentTime = 0L;
		if(EfamilyConstant.CONTENT_TYPE_VOICE == Long.valueOf(request.getData().getContentType())){
			contentTime = Long.valueOf(request.getData().getContentTime());
		}
		
		//*** 兼容版本：无appsendtime的版本 自动生成  appSendTime （会出现消息重发的可能）
		if(request.getData().getAppSendTime()==null){
			String appSendTime = DateUtils.format(new Date(),DateUtils.DATETIME_SENDTIME_FORMAT_PATTERN);
			request.getData().setAppSendTime(appSendTime);
		}
		
		EjlMessage em =ejlMessageServiceImpl.sendChatMessage(request.getCtx(),
				Long.valueOf(request.getUserId()),
				Long.valueOf(request.getData().getReceiveUserId()),
				Long.valueOf(request.getData().getReceiveUserType()),
				request.getData().getContent(),
				Long.valueOf(request.getData().getContentType()),
				contentTime,
				Long.valueOf(request.getData().getAppSendTime())
				);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		sendChatMessageResponse.setSendTime(String.valueOf(new Date().getTime()));
		sendChatMessageResponse.setMessageId(em.getId());
		sendChatMessageResponse.setAppSendTime(request.getData().getAppSendTime());
		fmlResponse.setData(sendChatMessageResponse);
		return fmlResponse;
	}
}
