package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.MessageCountInfo;
import com.winterframework.efamily.dto.MessageNoReadInfoResponse;
import com.winterframework.efamily.dto.MessageNoReadRequest;
import com.winterframework.efamily.dto.MessageNoReadResponse;
import com.winterframework.efamily.service.IEjlMessageMarkService;

/**
 * 聊天handler
 * 
 * @author jason
 * 
 */
@Controller("messageNoReadController")
@RequestMapping("/server")
public class MessageNoReadController{

	private static final Logger log = LoggerFactory.getLogger(MessageNoReadController.class);

	@Resource(name = "ejlMessageMarkServiceImpl")
	private IEjlMessageMarkService ejlMessageMarkServiceImpl;

	@RequestMapping("/messageNoRead")
	@ResponseBody
	protected Response<MessageNoReadResponse> doHandle(@RequestBody Request<MessageNoReadRequest> request) throws BizException, JsonProcessingException {
		Response<MessageNoReadResponse> fmlResponse = new Response<MessageNoReadResponse>(request);
		MessageNoReadResponse messageNoReadResponse = new MessageNoReadResponse();
		
		Long receiveUserId = Long.valueOf(request.getUserId());
		Long sendStatus = EfamilyConstant.MESSAGE_STATUS_NO_READ;//获取未读消息
		List<MessageNoReadInfoResponse> ejlLastMessageMarkList = new ArrayList<MessageNoReadInfoResponse>();
		List<MessageCountInfo> messageCountInfoList = ejlMessageMarkServiceImpl.getAllNoReadMessageCount(receiveUserId, sendStatus);
		if( messageCountInfoList.size() > 0){
             //*** 获取最后一条记录
			ejlLastMessageMarkList = ejlMessageMarkServiceImpl.getLashMessageByChatObj(receiveUserId);
			if(ejlLastMessageMarkList!=null && ejlLastMessageMarkList.size()>0){
				for(int i=0;i<ejlLastMessageMarkList.size();i++){
					MessageNoReadInfoResponse ejlMessageMarkTemp = ejlLastMessageMarkList.get(i);
					Long chatRoomId = ejlMessageMarkTemp.getChatRoomId();
					Long chatType = ejlMessageMarkTemp.getChatType();
					for(int j=0;j<messageCountInfoList.size();j++){
						MessageCountInfo messageCountInfoTemp = messageCountInfoList.get(j);
						if(messageCountInfoTemp.getChatRoomId().longValue()==chatRoomId.longValue()
						   &&messageCountInfoTemp.getChatType().longValue()==chatType.longValue()){
							ejlMessageMarkTemp.setNoReadMessageCount(messageCountInfoTemp.getMessageCount());
						}
					}
				}
			}
		}else{
			log.info("拉取未读聊天记录消息为空： messageCountInfoList = 0");
		}
		ObjectMapper mapper=new ObjectMapper();
		messageNoReadResponse.setMessageNoCountList(mapper.writeValueAsString(ejlLastMessageMarkList));
		fmlResponse.setData(messageNoReadResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
