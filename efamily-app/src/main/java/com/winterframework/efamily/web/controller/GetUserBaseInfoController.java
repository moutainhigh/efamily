package com.winterframework.efamily.web.controller;

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
import com.winterframework.efamily.dao.IEjlFamilyUserDao;
import com.winterframework.efamily.dao.IEjlUserFriendDao;
import com.winterframework.efamily.dto.GetUserBaseInfoRequest;
import com.winterframework.efamily.dto.GetUserBaseInfoResponse;
import com.winterframework.efamily.dto.UserBaseInfoStruc;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserFriend;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 获取用户基本信息handler
 * @author floy
 *
 */
@Controller("getUserBaseInfoController")
@RequestMapping("/server")
public class GetUserBaseInfoController {

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	@Resource(name = "ejlUserFriendDaoImpl")
	private IEjlUserFriendDao ejlUserFriendDaoImpl;

	@Resource(name = "ejlFamilyUserDaoImpl")
	private IEjlFamilyUserDao ejlFamilyUserDaoImpl;

	private static final Logger logger = LoggerFactory.getLogger(GetUserBaseInfoController.class);

	@RequestMapping("/getUserBaseInfo")
	@ResponseBody
	protected Response<GetUserBaseInfoResponse> doHandle(@RequestBody Request<GetUserBaseInfoRequest> request)
			throws BizException, JsonProcessingException {
		Response<GetUserBaseInfoResponse> fmlResponse = new Response<GetUserBaseInfoResponse>(request);
		GetUserBaseInfoResponse getUserBaseInfoResponse = new GetUserBaseInfoResponse();

		EjlUser response = ejlUserServiceImpl.getUserByUserId(request.getData().getUserId());
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		UserBaseInfoStruc struc = DTOConvert.convertEntityToUserBaseInfoStruc(response);
		struc.setRelation(EfamilyConstant.RELATION_OTHER);
		EjlUserFriend friend = new EjlUserFriend();
		friend.setUserId(request.getUserId());
		friend.setFriendId(request.getData().getUserId());
		friend = ejlUserFriendDaoImpl.selectOneObjByAttribute(friend);
		if (friend != null) {
			struc.setRemarkName(friend.getRemarkName() == null ? "" : friend.getRemarkName());
			if(friend.getStatus().longValue() == EfamilyConstant.USER_FRIEND_STATUS_SUCCESS){
				struc.setRelation(EfamilyConstant.RELATION_FRIEND);
			}
		}else{
			struc.setRemarkName("");
		}
		
		EjlUser user = ejlUserServiceImpl.getUserByUserId(request.getUserId());
		if(user.getFamilyId() != null){
			EjlFamilyUser familyUser = new EjlFamilyUser();
			familyUser.setUserId(request.getData().getUserId());
			familyUser.setFamilyId(user.getFamilyId());
			familyUser.setStatus(EfamilyConstant.STATUS_SUCCESS);
			EjlFamilyUser ejlFamilyUser = ejlFamilyUserDaoImpl.selectOneObjByAttribute(familyUser);
			if (ejlFamilyUser != null) { 
				struc.setRelation(EfamilyConstant.RELATION_FAMILY);
			}
		}
		getUserBaseInfoResponse.setUserBaseInfo(mapper.writeValueAsString(struc));
		fmlResponse.setData(getUserBaseInfoResponse);
		logger.info(mapper.writeValueAsString(response));
		return fmlResponse;
	}
}
