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
import com.winterframework.efamily.dto.ManageFriendShipRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlUserFriendService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.utils.StringHelper;

/**
 * 
* @ClassName: ManageFriendShipHandler 
* @Description: TODO(管理好友：添加和接受) 
* @author jason 
* @date 2015-6-24 下午1:59:48 
*
 */
@Controller("manageFriendShipController")
@RequestMapping("/server")
public class ManageFriendShipController  {
	private static final Logger logger = LoggerFactory.getLogger(ManageFriendShipController.class);
	
	@Resource(name="ejlUserFriendServiceImpl")
	private IEjlUserFriendService ejlUserFriendServiceImpl;
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/manageFriendShip")
	@ResponseBody
	protected Response<ManageFriendShipRequest> doHandle(@RequestBody Request<ManageFriendShipRequest> request) throws Exception {
		Response<ManageFriendShipRequest> fmlResponse = new Response<ManageFriendShipRequest>(request);
		ManageFriendShipRequest createChatGroupResponse = new ManageFriendShipRequest();
		
        if(request.getData().getCustomerId()==null||request.getData().getOprType()==null||request.getData().getIsPhoneNumber()==null){
        	logger.error("管理好友关系时，参数不完整，操作失败。 userOperate = "+request.getUserId()+" ; " +
        			"; customerId = "+request.getData().getCustomerId()+            			
        			"; oprType = "+request.getData().getOprType()+            			
        			"; isPhoneNumber = "+request.getData().getIsPhoneNumber());
        	throw new BizException(StatusBizCode.PARAM_INCOMPLETE.getValue());
		}
		String customerId = request.getData().getCustomerId();//请求接受方的用户ID
		Long oprType = request.getData().getOprType();//请求的类型，1:“添加”请求，2:“接受”请求
		Long isPhoneNumber = request.getData().getIsPhoneNumber();//请求的类型，1:“添加”请求，2:“接受”请求
		
	    //****  未进行业务处理  
		//TODO---------------------------------
		//*** “添加（邀请）”：如果被邀请的人不是我们的系统用户，我们系统发送短信，
		//，如果是系统用户则直接推送“加好友”的请求。
		if(EfamilyConstant.USER_SYSTEM == isPhoneNumber || EfamilyConstant.USER_PHONE == isPhoneNumber){
			//*** 后期会缓存用户信息
			EjlUser user = ejlUserServiceImpl.get(request.getUserId());
			
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			createChatGroupResponse.setCustomerId(customerId);
			createChatGroupResponse.setIsPhoneNumber(isPhoneNumber);
			createChatGroupResponse.setOprType(oprType);
			fmlResponse.setData(createChatGroupResponse);
			
			Long userId = null;
			if(EfamilyConstant.USER_PHONE == isPhoneNumber){
				//*** 判断此手机号码是否为系统用户
				customerId = StringHelper.getPhone(customerId);
				EjlUser userPhone = ejlUserServiceImpl.getUserByPhone(customerId);
				if(userPhone == null){
					//*** 此手机号码不是系统用户 只发送短信 进行提醒
					//------- TODO  发送短信    ---------
					logger.info("发送短信短信短信短信短信短信短信 : ----- userId = "+request.getUserId()+" =====>>>>  applyUserid = "+request.getData().getCustomerId());
					throw new BizException(StatusBizCode.USER_UN_VALID.getValue());
				}else{
					userId = userPhone.getId();
				}
			}else{
				userId = Long.valueOf(customerId);
			}
			
			ejlUserFriendServiceImpl.manageUserFriend(request.getCtx(),request.getUserId(), userId, Long.valueOf(oprType));
			
          	Map<String, String> paramMap = new HashMap<String,String>();
          	paramMap.put("userId", String.valueOf(request.getUserId()));
          	paramMap.put("userName", user.getNickName());
          	paramMap.put("icon", user.getHeadImg());
          	paramMap.put("type", String.valueOf(oprType));
          	paramMap.put("phone", user.getPhone());
          	NoticeType noticeType = null;
          	if(EfamilyConstant.USER_FRIEND_ACCEPT == Long.valueOf(oprType) ){
          		noticeType = NoticeType.MANAGE_FRIEND_USER_AGREE;
          	}else{
          		noticeType = NoticeType.MANAGE_FRIEND_USER;
          	}
          	ejlUserServiceImpl.notifyForManageFriendShip(paramMap, request.getUserId(),userId, noticeType);
			
		}else{
			logger.error("操作的用户类型未定义，操作失败。 userOperate = "+request.getUserId()+" ; customerId = "+customerId);
        	throw new BizException(StatusBizCode.USER_UN_VALID.getValue());
		}
		return fmlResponse;
	}
}