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
import com.winterframework.efamily.dto.FamilyUserListRequest;
import com.winterframework.efamily.dto.FamilyUserListResponse;
import com.winterframework.efamily.dto.UserStruc;
import com.winterframework.efamily.service.IEjlFamilyUserService;
/**
 * 获取家庭成员列表handler
 * @author david
 *
 */
@Controller("familyUserListController")
@RequestMapping("/server")
public class FamilyUserListController{
	private static final Logger log = LoggerFactory.getLogger(FamilyUserListController.class); 
	@Resource(name="ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@RequestMapping("/familyUserList")
	@ResponseBody
	protected Response<FamilyUserListResponse> doHandle(@RequestBody Request<FamilyUserListRequest> request) throws BizException, JsonProcessingException {
		Response<FamilyUserListResponse> fmlResponse = new Response<FamilyUserListResponse>(request);
		FamilyUserListResponse familyUserListResponse = new FamilyUserListResponse();
		
		List<UserStruc> response=ejlFamilyUserServiceImpl.getFamilyUserList(request.getUserId(),request.getData().getFamilyId(),request.getData().getUserType());
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper=new ObjectMapper();
		familyUserListResponse.setUserList(mapper.writeValueAsString(response));
		fmlResponse.setData(familyUserListResponse);
		return fmlResponse;
	}
}