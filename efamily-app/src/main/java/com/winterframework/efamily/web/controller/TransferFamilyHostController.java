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
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.TransferFamilyHostRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserService;

	/**
	 * 
	* @ClassName: TransferFamilyHostHandler 
	* @Description: TODO(管理转让家庭) 
	* @author jason 
	* @date 2015-6-24 下午1:59:48 
	*
	 */
	@Controller("transferFamilyHostController")
	@RequestMapping("/server")
	public class TransferFamilyHostController  {
		private static final Logger logger = LoggerFactory.getLogger(TransferFamilyHostController.class);
		
		@Resource(name="ejlFamilyUserServiceImpl")
		private IEjlFamilyUserService ejlFamilyUserServiceImpl;
		
		@Resource(name="ejlUserServiceImpl")
		private IEjlUserService ejlUserServiceImpl;
		
		@RequestMapping("/transferFamilyHost")
		@ResponseBody
		protected Response<TransferFamilyHostRequest> doHandle(@RequestBody Request<TransferFamilyHostRequest> request) throws Exception {
			Response<TransferFamilyHostRequest> fmlResponse = new Response<TransferFamilyHostRequest>(request);
			TransferFamilyHostRequest createChatGroupResponse = new TransferFamilyHostRequest();
			
			Long userId = request.getData().getUserId();
			Long familyId = request.getData().getFamilyId();
		
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(createChatGroupResponse);
			
			ejlFamilyUserServiceImpl.TransferFamilyHost(request.getCtx(),familyId, userId, request.getUserId());
			EjlUser user = ejlUserServiceImpl.get(userId);
          	Map<String, String> paramMap = new HashMap<String,String>();
          	paramMap.put("familyId", String.valueOf(familyId));
          	paramMap.put("userId", String.valueOf(userId));
          	paramMap.put("nickName", user.getNickName());
          	ejlUserServiceImpl.notifyForTransferFamilyHost(paramMap, userId,request.getUserId(),familyId,NoticeType.TRANSFER_FAMILY_HOST);
          	
			return fmlResponse;
		}
	}
