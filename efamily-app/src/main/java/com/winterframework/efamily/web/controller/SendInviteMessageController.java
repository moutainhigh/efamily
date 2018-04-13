/**   
* @Title: SendInviteMessageHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-20 上午10:47:41 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.util.Date;

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
import com.winterframework.efamily.dto.SendInviteMessageRequest;
import com.winterframework.efamily.dto.SendInviteMessageResponse;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlVerifyCode;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.service.IEjlVerifyCodeService;

/** 
* @ClassName: SendInviteMessageHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-20 上午10:47:41 
*  
*/
/**
 * 发送邀请信息handler
 * @author floy
 *
 */
@Controller("sendInviteMessageController")
@RequestMapping("/server")
public class SendInviteMessageController {

	@Resource(name = "ejlVerifyCodeServiceImpl")
	private IEjlVerifyCodeService ejlVerifyCodeServiceImpl;

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(SaveUserBaseInfoController.class);

	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param request
	* @return 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(com.winterframework.efamily.server.core.ServerContext, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@RequestMapping("/sendInviteMessage")
	@ResponseBody
	protected Response<SendInviteMessageResponse> doHandle(@RequestBody Request<SendInviteMessageRequest> request) throws BizException {
		Response<SendInviteMessageResponse> fmlResponse = new Response<SendInviteMessageResponse>(request);
		SendInviteMessageResponse sendInviteMessageResponse = new SendInviteMessageResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		EjlVerifyCode ejlVerifyCode = new EjlVerifyCode();
		ejlVerifyCode.setUserId(request.getData().getUserId());
		ejlVerifyCode.setFamilyId(request.getData().getFamilyId());
		ejlVerifyCode.setPhoneNumber(request.getData().getInvitePhoneNumber());
		ejlVerifyCode.setIsValid(1l);
		ejlVerifyCode.setGmtCreated(new Date());
		ejlVerifyCode.setType(2l);
		//ejlVerifyCode.setVerifyCode(FormulaUtils.getVerifyCode());
		ejlVerifyCode.setTimeOut(60l);
		ejlVerifyCodeServiceImpl.save(request.getCtx(),ejlVerifyCode);
		EjlUser user = ejlUserServiceImpl.getUserByUserId(Long.valueOf(request.getData().getUserId()));
		logger.info(ejlVerifyCode.getPhoneNumber() + ":" + ejlVerifyCode.getPhoneNumber());
		fmlResponse.setData(sendInviteMessageResponse);
		return fmlResponse;
	}

}
