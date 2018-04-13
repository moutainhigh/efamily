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
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.ChatGroupDetailsList;
import com.winterframework.efamily.dto.GetChatGroupDetailsRequest;
import com.winterframework.efamily.dto.GetChatGroupDetailsResponse;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 
* @ClassName: GetChatGroupDetailsHandler 
* @Description: TODO(获取群组信息) 
* @author jason 
* @date 2015-6-24 上午11:16:22 
*
 */
@Controller("getChatGroupDetailsController")
@RequestMapping("/server")
public class GetChatGroupDetailsController{
	private static final Logger logger = LoggerFactory.getLogger(GetChatGroupDetailsController.class);
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/getChatGroupDetails")
	@ResponseBody
	protected Response<GetChatGroupDetailsResponse> doHandle(@RequestBody Request<GetChatGroupDetailsRequest> request) throws BizException, JsonProcessingException {
		Response<GetChatGroupDetailsResponse> fmlResponse = new Response<GetChatGroupDetailsResponse>(request);
		GetChatGroupDetailsResponse getChatGroupDetailsResponse = new GetChatGroupDetailsResponse();
		Long chatGroupId = request.getData().getChatGroupId();
		Long chatType = request.getData().getChatType();
		if(chatGroupId != null){
			List<ChatGroupDetailsList>  response  = ejlUserServiceImpl.getChatRoomDetails(request.getCtx(),request.getUserId(),chatGroupId,chatType);
			ObjectMapper mapper=new ObjectMapper();
			getChatGroupDetailsResponse.setMemberInfos(mapper.writeValueAsString(response));
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(getChatGroupDetailsResponse);
		}else{
			fmlResponse.setStatus(new Status(StatusCode.FAILED.getValue()));
			logger.warn("获取群组信息失败，群组ID为空。userId = "+request.getUserId()+" ; chatGroupId ="+chatGroupId);
		}
		return fmlResponse;
	}
}