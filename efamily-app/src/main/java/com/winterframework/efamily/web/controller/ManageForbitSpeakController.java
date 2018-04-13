	package com.winterframework.efamily.web.controller;

	import java.util.HashMap;
import java.util.Map;

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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.ManageForbitSpeakRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserFriendService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.utils.StringHelper;

	/**
	 * 
	* @ClassName: ManageForbitSpeakHandler 
	* @Description: TODO(管理禁言：禁言 取消禁言) 
	* @author jason 
	* @date 2015-6-24 下午1:59:48 
	*
	 */
	@Controller("manageForbitSpeakController")
	@RequestMapping("/server")
	public class ManageForbitSpeakController  {
		private static final Logger logger = LoggerFactory.getLogger(ManageForbitSpeakController.class);
		
		@Resource(name="ejlFamilyUserServiceImpl")
		private IEjlFamilyUserService ejlFamilyUserServiceImpl;
		
		@Resource(name="ejlUserServiceImpl")
		private IEjlUserService ejlUserServiceImpl;
		
		@RequestMapping("/manageForbitSpeak")
		@ResponseBody
		protected Response<ManageForbitSpeakRequest> doHandle(@RequestBody Request<ManageForbitSpeakRequest> request) throws Exception {
			Response<ManageForbitSpeakRequest> fmlResponse = new Response<ManageForbitSpeakRequest>(request);
			ManageForbitSpeakRequest createChatGroupResponse = new ManageForbitSpeakRequest();
			
			/**
			 *   userId：被禁言的用户ID
		     *   chatType：聊天室类型  
		     *   chatRoomId:聊天室ID 
		     *   oprType: 1 禁言 2 取消禁言
			 */
			
			Integer oprType = request.getData().getOprType();
			Long userId = request.getData().getUserId();
			Long chatRoomId = request.getData().getChatRoomId();
			Long chatType = request.getData().getChatType();
		
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(createChatGroupResponse);
			
			ejlFamilyUserServiceImpl.manageForbitSpeak(request.getCtx(), userId, chatType, chatRoomId, oprType);
			
          	Map<String, String> paramMap = new HashMap<String,String>();
          	paramMap.put("chatRoomId", String.valueOf(chatRoomId));
          	paramMap.put("oprType", String.valueOf(oprType));
          	paramMap.put("chatType", String.valueOf(chatType));
          	ejlUserServiceImpl.notifyForManageForbitSpeak(paramMap, userId, NoticeType.FORBIT_SPEAK);
			
			//logger.error("操作的用户类型未定义，操作失败。 userOperate = "+request.getUserId()+" ; customerId = "+customerId);
        	//throw new BizException(StatusBizCode.USER_UN_VALID.getValue());
          	
			return fmlResponse;
		}
	}
