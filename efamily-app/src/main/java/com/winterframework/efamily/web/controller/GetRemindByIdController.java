/**   
* @Title: GetRemindByIdHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-10 下午2:06:55 
* @version V1.0   
*/
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
import com.winterframework.efamily.dao.IEjlUserFriendDao;
import com.winterframework.efamily.dto.GetRemindByIdRequest;
import com.winterframework.efamily.dto.GetRemindByIdResponse;
import com.winterframework.efamily.dto.RemindStruc;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserFriend;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IEjlRemindService;
import com.winterframework.efamily.service.IEjlUserService;

/** 
* @ClassName: GetRemindByIdHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-10 下午2:06:55 
*  
*/
@Controller("getRemindByIdController")
@RequestMapping("/server")
public class GetRemindByIdController {

	@Resource(name = "ejlRemindServiceImpl")
	private IEjlRemindService ejlRemindServiceImpl;
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name="ejlUserFriendDaoImpl")
	private IEjlUserFriendDao ejlUserFriendDaoImpl;

	private static final Logger logger = LoggerFactory.getLogger(GetRemindByIdController.class);

	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param request
	* @return 
	 * @throws JsonProcessingException 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(com.winterframework.efamily.server.core.ServerContext, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@RequestMapping("/getRemindById")
	@ResponseBody
	protected Response<GetRemindByIdResponse> doHandle(@RequestBody Request<GetRemindByIdRequest> request) throws BizException, JsonProcessingException {
		Response<GetRemindByIdResponse> fmlResponse = new Response<GetRemindByIdResponse>(request);
		GetRemindByIdResponse getRemindByIdResponse = new GetRemindByIdResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		EjlRemind ejlRemind = ejlRemindServiceImpl.queryReminById(request.getData().getRemindId(),request.getData().getType());
		Long sendUserId = ejlRemind.getSendUserId();
		EjlUserFriend friend = new EjlUserFriend();;
		if (request.getData().getType().intValue() == 2) {
			EjlRemind read = new EjlRemind();
			read.setId(ejlRemind.getId());
			read.setRemindState(1l);
			ejlRemindServiceImpl.updateRecord(read);
			sendUserId = ejlRemind.getUserId();
			friend.setUserId(ejlRemind.getSendUserId());
			friend.setFriendId(sendUserId);
		}else{
			sendUserId = ejlRemind.getSendUserId();
			friend.setUserId(ejlRemind.getUserId());
			friend.setFriendId(sendUserId);
		}
		
		EjlUser user = ejlUserServiceImpl.getUserByUserId(sendUserId);
		RemindStruc struc = DTOConvert.convertEntityToRemindStruc(ejlRemind,
				user != null ? user.getNickName() == null ? user.getPhone() : user.getNickName() : null);
		struc.setHeadImg(user.getHeadImg());
		struc.setUserType(user.getType());
		friend = ejlUserFriendDaoImpl.selectOneObjByAttribute(friend);
		
		if(friend!=null&&friend.getRemark()!=null){
			struc.setRemarkName(friend.getRemark());
		}else{
			if(user != null){
				struc.setRemarkName(user.getNickName()!=null?user.getNickName():user.getUserName());
			}
		}
		struc.setDeliverUserId(sendUserId);
		getRemindByIdResponse.setRemind(mapper.writeValueAsString(struc));
		fmlResponse.setData(getRemindByIdResponse);
		logger.info(mapper.writeValueAsString(ejlRemind));
		return fmlResponse;
	}

}
