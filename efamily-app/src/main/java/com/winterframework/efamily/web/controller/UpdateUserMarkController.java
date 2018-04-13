/**   
* @Title: UpdateUserMarkHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-2 下午5:48:59 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.UpdateUserMarkRequest;
import com.winterframework.efamily.dto.UpdateUserMarkResponse;
import com.winterframework.efamily.service.IEjlUserFriendService;

/** 
* @ClassName: UpdateUserMarkHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-2 下午5:48:59 
*  
*/
@Controller("updateUserMarkController")
@RequestMapping("/server")
public class UpdateUserMarkController {

	@Resource(name = "ejlUserFriendServiceImpl")
	private IEjlUserFriendService ejlUserFriendServiceImpl;

	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param req
	* @return 
	 * @throws Exception 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(com.winterframework.efamily.server.core.ServerContext, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@RequestMapping("/updateUserMark")
	@ResponseBody
	protected Response<UpdateUserMarkResponse> doHandle(@RequestBody Request<UpdateUserMarkRequest> request) throws Exception {
		Response<UpdateUserMarkResponse> fmlResponse = new Response<UpdateUserMarkResponse>(request);
		UpdateUserMarkResponse updateUserMarkResponse = new UpdateUserMarkResponse();
		
		Long userId = request.getUserId();
		Long friendUserId = request.getData().getUserId();
		String remarkName = request.getData().getRemarkName();
		ejlUserFriendServiceImpl.updateUserFriend(request.getCtx(),userId,friendUserId,remarkName);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(updateUserMarkResponse);
		return fmlResponse;
	}

}
