/**   
* @Title: GetRemindByIdHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-10 下午2:06:55 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
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
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlUserFriendDao;
import com.winterframework.efamily.dto.GetUserRemindListRequest;
import com.winterframework.efamily.dto.GetUserRemindListResponse;
import com.winterframework.efamily.dto.RemindListStruc;
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
@Controller("getUserRemindListController")
@RequestMapping("/server")
public class GetUserRemindListController{

	@Resource(name = "ejlRemindServiceImpl")
	private IEjlRemindService ejlRemindServiceImpl;
	
	@Resource(name="ejlUserFriendDaoImpl")
	private IEjlUserFriendDao ejlUserFriendDaoImpl;
	
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

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
	@RequestMapping("/getUserRemindList")
	@ResponseBody
	protected Response<GetUserRemindListResponse> doHandle(@RequestBody Request<GetUserRemindListRequest> request) throws BizException, JsonProcessingException {
		Response<GetUserRemindListResponse> fmlResponse = new Response<GetUserRemindListResponse>(request);
		GetUserRemindListResponse getUserRemindListResponse = new GetUserRemindListResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		List<EjlRemind> ejlReminds = ejlRemindServiceImpl.queryAllRemindsForUser(request.getUserId());
		List<EjlRemind> ejlReceiveReminds = ejlRemindServiceImpl.queryAllReceiveRemindsForUser(request.getUserId());
		List<RemindListStruc> list = new ArrayList<RemindListStruc>();
		for (EjlRemind remind : ejlReceiveReminds) {
			RemindListStruc s = DTOConvert.convertEntityToRemindListStruc(remind,2l);
			Long userId = remind.getUserId();
			Long sendUserId = remind.getSendUserId();
			EjlUserFriend friend = new EjlUserFriend();
			friend.setUserId(sendUserId);
			friend.setFriendId(userId);
			friend = ejlUserFriendDaoImpl.selectOneObjByAttribute(friend);
			if(friend!=null&&friend.getRemark()!=null){
				s.setCreator(friend.getRemark());
			}else{
				EjlUser user = ejlUserServiceImpl.getUserByUserId(userId);
				if(user != null){
					s.setCreator(user.getNickName()!=null?user.getNickName():user.getUserName());
				}
			}
			EjlUser user = ejlUserServiceImpl.getUserByUserId(userId);
			s.setDeliverUserId(userId);
			s.setHeadImg(user.getHeadImg());
			s.setRemarkName(s.getCreator());
			s.setRemindContent(remind.getContent());
			s.setDurationTime(remind.getDurationTime());
			s.setDeliverTime(remind.getReceiveTime()==null?DateUtils.convertDate2Long(remind.getSendTime()):DateUtils.convertDate2Long(remind.getReceiveTime()));
			list.add(s);
		}
		for (EjlRemind remind : ejlReminds) {
			RemindListStruc s = DTOConvert.convertEntityToRemindListStruc(remind,1l);
			
			Long sendUserId = remind.getSendUserId();
			EjlUserFriend friend = new EjlUserFriend();
			friend.setUserId(remind.getUserId());
			friend.setFriendId(sendUserId);
			friend = ejlUserFriendDaoImpl.selectOneObjByAttribute(friend);
			if(friend!=null&&friend.getRemark()!=null){
				s.setCreator(friend.getRemark());
			}else{
				EjlUser user = ejlUserServiceImpl.getUserByUserId(sendUserId);
				if(user != null){
					s.setCreator(user.getNickName()!=null?user.getNickName():user.getUserName());
				}
			}
			
			EjlUser user = ejlUserServiceImpl.getUserByUserId(sendUserId);
			s.setDeliverUserId(sendUserId);
			s.setHeadImg(user.getHeadImg());
			s.setRemarkName(s.getCreator());
			s.setRemindContent(remind.getContent());
			s.setDurationTime(remind.getDurationTime());
			s.setDeliverTime(remind.getReceiveTime()==null?DateUtils.convertDate2Long(remind.getSendTime()):DateUtils.convertDate2Long(remind.getReceiveTime()));
			list.add(s);
		}
		getUserRemindListResponse.setRemindList(mapper.writeValueAsString(list));
		fmlResponse.setData(getUserRemindListResponse);
		logger.info(mapper.writeValueAsString(list));
		return fmlResponse;
	}
}
