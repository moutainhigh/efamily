package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.winterframework.efamily.dto.CreateChatGroupRequest;
import com.winterframework.efamily.dto.CreateChatGroupResponse;
import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.service.IEjlUserService;

/**
* 
* @ClassName: CreateChatGroupHandler 
* @Description: TODO(创建聊天群组，并且往聊天群组中加入用户) 
* @author jason 
* @date 2015-6-24 上午9:47:47 
*
 */ 
@Controller("createChatGroupController")
@RequestMapping("/server")
public class CreateChatGroupController {
	private static final Logger logger = LoggerFactory.getLogger(CreateChatGroupController.class);
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/createChatGroup")
	@ResponseBody
	protected Response<CreateChatGroupResponse> doHandle(@RequestBody Request<CreateChatGroupRequest> request) throws BizException {
		Response<CreateChatGroupResponse> fmlResponse = new Response<CreateChatGroupResponse>(request);
		String memberIds = request.getData().getMemberIds();
		if(StringUtils.isNotBlank(memberIds)){
			//*** 新建聊天群  和 (群组用户关系) 
			EjlChartRoom ejlChartRoom = ejlUserServiceImpl.createChatRoom(request.getCtx(),request.getUserId());
            if(ejlChartRoom.getId() != null){
            	
            	CreateChatGroupResponse createChatGroupResponse = new CreateChatGroupResponse();
            	ejlUserServiceImpl.joinUserChatGroupInfo(request.getCtx(),memberIds, ejlChartRoom.getId());
            	createChatGroupResponse.setChatGroupId(ejlChartRoom.getId());
            	createChatGroupResponse.setChatRoomName(ejlChartRoom.getName());
            	fmlResponse.setData(createChatGroupResponse);
            	fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
            }else{
            	fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
				logger.warn("创建聊天群组失败。userId = "+request.getUserId()+" ; memberIds ="+memberIds);
            }
		}else{
			fmlResponse.setStatus(new Status(StatusCode.FAILED.getValue()));
			logger.warn("创建聊天群组，成员为空，创建失败。userId = "+request.getUserId()+" ; memberIds ="+memberIds);
		}
		return fmlResponse;
	}
}