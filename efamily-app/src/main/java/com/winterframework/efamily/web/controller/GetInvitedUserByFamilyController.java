/**   
* @Title: GetInvitedUserByFamilyHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-20 下午4:29:43 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
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
import com.winterframework.efamily.dto.GetInvitedUserByFamilyRequest;
import com.winterframework.efamily.dto.GetInvitedUserByFamilyResponse;
import com.winterframework.efamily.dto.InviteUserStruc;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlVerifyCode;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.service.IEjlVerifyCodeService;

/** 
* @ClassName: GetInvitedUserByFamilyHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-20 下午4:29:43 
*  
*/
@Controller("getInvitedUserByFamilyController")
@RequestMapping("/server")
public class GetInvitedUserByFamilyController{

	@Resource(name = "ejlVerifyCodeServiceImpl")
	private IEjlVerifyCodeService ejlVerifyCodeServiceImpl;

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(GetInvitedUserByFamilyController.class);

	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param request
	* @return 
	 * @throws JsonProcessingException 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(com.winterframework.efamily.server.core.ServerContext, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@RequestMapping("/getInvitedUserByFamily")
	@ResponseBody
	protected Response<GetInvitedUserByFamilyResponse> doHandle(@RequestBody Request<GetInvitedUserByFamilyRequest> request) throws BizException, JsonProcessingException {
		Response<GetInvitedUserByFamilyResponse> fmlResponse = new Response<GetInvitedUserByFamilyResponse>(request);
		GetInvitedUserByFamilyResponse getInvitedUserByFamilyResponse = new GetInvitedUserByFamilyResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		List<EjlVerifyCode> list = ejlVerifyCodeServiceImpl.getInvitedUserByFamily(request.getData().getFamilyId());
		List<InviteUserStruc> resultList = new ArrayList<InviteUserStruc>();
		if (!list.isEmpty()) {
			for (EjlVerifyCode ejlVerifyCode : list) {
				EjlUser user = ejlUserServiceImpl.getUserByPhone(ejlVerifyCode.getPhoneNumber());
				InviteUserStruc result = new InviteUserStruc();
				if (user == null) {
					result.setPhoneNumber(ejlVerifyCode.getPhoneNumber());
				} else {
					result.setUserName(user.getUserName() == null ? "" : user.getUserName());
					result.setUserId(user.getId());
				}
				if (ejlVerifyCode.getStatus() != null) {
					if (ejlVerifyCode.getStatus() == 0) {
						result.setOprType(1);
					} else {
						result.setOprType(2);
					}
				} else if (ejlVerifyCode.getTimeOut() != null) {
					if (DateUtils.addMinutes(ejlVerifyCode.getGmtCreated(), ejlVerifyCode.getTimeOut().intValue())
							.before(new Date())) {
						result.setOprType(4);
					} else {
						result.setOprType(3);
					}
				} else {
					result.setOprType(3);
				}
				resultList.add(result);
			}
			getInvitedUserByFamilyResponse.setInvitedUserList(mapper.writeValueAsString(resultList));
			fmlResponse.setData(getInvitedUserByFamilyResponse);
		}

		return fmlResponse;
	}
}
