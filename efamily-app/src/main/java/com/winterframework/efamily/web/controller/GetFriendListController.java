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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.FriendListResponse;
import com.winterframework.efamily.dto.GetFriendListRequest;
import com.winterframework.efamily.dto.GetFriendListResponse;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlUserService;

@Controller("getFriendListController")
@RequestMapping("/server")
public class GetFriendListController{
	private static final Logger log = LoggerFactory.getLogger(GetFriendListController.class); 
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/getFriendList")
	@ResponseBody
	protected Response<GetFriendListResponse> doHandle(@RequestBody Request<GetFriendListRequest> request) throws BizException, JsonProcessingException {
		Response<GetFriendListResponse> fmlResponse = new Response<GetFriendListResponse>(request);
		GetFriendListResponse getFriendListResponse = new GetFriendListResponse();
		
		int isIncludeFamilyMember = request.getData().getIsIncludeMember();
		List<FriendListResponse> response=ejlUserServiceImpl.getEjlFriendListByUserId(request.getUserId(),EfamilyConstant.USER_TYPE_APP);
		ObjectMapper mapper=new ObjectMapper();
		getFriendListResponse.setUserList(mapper.writeValueAsString(response));
		
		if(isIncludeFamilyMember == EfamilyConstant.FRIEND_QUERY_INCLUDE_FAMILY_MEMBER){
			List<FriendListResponse> responseFamily=ejlUserServiceImpl.getEjlFamilyListByUserId(request.getUserId(),null);
			getFriendListResponse.setFamilyUserList(mapper.writeValueAsString(responseFamily));
		}
		
		fmlResponse.setData(getFriendListResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			
		return fmlResponse;
	}
}