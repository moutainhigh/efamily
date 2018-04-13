package com.winterframework.efamily.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.winterframework.efamily.dto.ManageGroupSettingRequest;
import com.winterframework.efamily.dto.ManageGroupSettingResponse;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUserChartRoom;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlUserChartRoomService;
import com.winterframework.efamily.service.IEjlUserService;


@Controller("manageGroupSettingController")
@RequestMapping("/server")
public class ManageGroupSettingController{
	private static final Logger logger = LoggerFactory.getLogger(ManageGroupSettingController.class);
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name="ejlUserChartRoomServiceImpl")
	private IEjlUserChartRoomService ejlUserChartRoomServiceImpl;
	
	
	@RequestMapping("/manageGroupSetting")
	@ResponseBody
	protected Response<ManageGroupSettingResponse> doHandle(@RequestBody Request<ManageGroupSettingRequest> request) throws BizException, JsonProcessingException {
		Response<ManageGroupSettingResponse> fmlResponse = new Response<ManageGroupSettingResponse>(request);
		ManageGroupSettingResponse manageGroupSettingResponse = new ManageGroupSettingResponse();
		
		Long userId = request.getUserId();
		String chatGroupId = request.getData().getChatGroupId()+"";
		String parameterIndex = request.getData().getParameterIndex();
		String parameterContext = request.getData().getParameterContext();
		Long oprType = request.getData().getOprType();
		Long chatType = request.getData().getChatType();
		EjlUserChartRoom ejlUserChartRoom = null;
		
		if(EfamilyConstant.CHAT_TYPE_ROOM == chatType.longValue()){
			ejlUserChartRoom = ejlUserChartRoomServiceImpl.getByUserIdAndChatRoomId(userId, Long.valueOf(chatGroupId));
			if(ejlUserChartRoom == null || ( ejlUserChartRoom.getStatus()!=null && ejlUserChartRoom.getStatus()==1 )){
				logger.warn("管理群组信息，用户已经不在群组中，操作失败。userId = "+request.getUserId()+"; chatGroupId = "+chatGroupId);
				throw new BizException(StatusBizCode.CHAT_ROOM_USER_NOT.getValue());
			}
		}
        //******* 管理群组信息 ：单个属性的设置和获取*****
	    parameterContext = ejlUserServiceImpl.manageUserChatGroupInfo(request.getCtx(),userId, parameterIndex,parameterContext,chatGroupId,oprType,chatType);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		manageGroupSettingResponse.setChatGroupId(Long.valueOf(chatGroupId));
		manageGroupSettingResponse.setParameterContext(parameterContext);
		manageGroupSettingResponse.setParameterIndex(parameterIndex);
		fmlResponse.setData(manageGroupSettingResponse);
		//*** 群名称修改后 进行推送  *****************
		if(EfamilyConstant.CHAT_TYPE_ROOM == chatType.longValue()){
			if(EfamilyConstant.GROUP_SETTING_NAME.equals(parameterIndex)){
          		Map<String, String> paramMap = new HashMap<String,String>();
	          	paramMap.put("groupId", chatGroupId);
	          	paramMap.put("groupName", parameterContext);
	          	paramMap.put("parameterIndex", parameterIndex);
	          	ejlUserServiceImpl.notifyForManageGroupSetting(paramMap, request.getUserId(),Long.valueOf(chatGroupId),NoticeType.MANAGE_GROUP_SETTING,parameterContext);
	          //*** 加入群组、退出群组、删除群组成员   进行推送  *****************
          	}else if(EfamilyConstant.GROUP_ROOM_JOIN.equals(parameterIndex)
          			||EfamilyConstant.GROUP_ROOM_QUIT.equals(parameterIndex)
          			||EfamilyConstant.GROUP_ROOM_DELETE.equals(parameterIndex)){
          		/**
	          	｛userId（发起人的用户ID）,userName（发起人的用户名称）,icon(头像),groupId
				(群组ID)，groupName(群组名称)，parameterIndex（200061：加入群组，200063：退出群组，200065：删除群组成员），sex: 0 男，1 女｝
	          	 */
        			Map<String, String> paramMap = new HashMap<String,String>();
		          	ObjectMapper mapper = new ObjectMapper();
					paramMap.put("userInfoList", mapper.writeValueAsString(ejlUserServiceImpl.getUserByMoreUserId(Arrays.asList(parameterContext.split(",")))));
		          	paramMap.put("groupId", String.valueOf(chatGroupId));
		          	paramMap.put("groupName", ejlUserChartRoom.getChatRoomName());
		          	paramMap.put("parameterIndex", parameterIndex);
		          	ejlUserServiceImpl.notifyForManageGroupSetting(paramMap, request.getUserId(),Long.valueOf(chatGroupId),NoticeType.MANAGE_GROUP_SETTING,parameterContext);
          	}
		}

		return fmlResponse;
	}
}