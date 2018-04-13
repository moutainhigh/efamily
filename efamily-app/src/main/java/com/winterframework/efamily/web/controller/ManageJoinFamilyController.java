/**   
* @Title: ManageJoinFamilyHandler.java 
* @Package com.winterframework.efamily.server.handler 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-11 上午9:53:12 
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
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.ManageJoinFamilyCheck;
import com.winterframework.efamily.dto.ManageJoinFamilyRequest;
import com.winterframework.efamily.dto.ManageJoinFamilyResponse;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserService;

/** 
* @ClassName: ManageJoinFamilyHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-11 上午9:53:12 
*  
*/
@Controller("manageJoinFamilyController")
@RequestMapping("/server")
public class ManageJoinFamilyController {

	private static final Logger logger = LoggerFactory.getLogger(ManageJoinFamilyController.class);
	@Resource(name = "ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;

	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	/**
	* Title: doHandle
	* Description:
	* @param ctx
	* @param request
	* @return 
	* @see com.winterframework.efamily.server.core.AbstractHandler#doHandle(com.winterframework.efamily.server.core.ServerContext, com.winterframework.efamily.server.protocol.FmlRequest) 
	*/
	@RequestMapping("/manageJoinFamily")
	@ResponseBody
	protected Response<ManageJoinFamilyResponse> doHandle(@RequestBody Request<ManageJoinFamilyRequest> request) throws BizException {
		Response<ManageJoinFamilyResponse> fmlResponse = new Response<ManageJoinFamilyResponse>(request);
	    Long isPhoneNumber = request.getData().getIsPhoneNumber();//1 手机用户  2 系统用户
	    String familyCode = request.getData().getFamilyCode();
	    String familyId = request.getData().getFamilyId();
	    String applyUseridX = request.getData().getApplyUserid();
	    String manageType = request.getData().getManageType();
	    Long userId =request.getUserId();
	    
	    ManageJoinFamilyResponse manageJoinFamilyResponse = new ManageJoinFamilyResponse();
	    ManageJoinFamilyCheck  manageJoinFamilyCheck  = ejlFamilyServiceImpl.checkManageFamily(userId, applyUseridX, familyId, familyCode, manageType, isPhoneNumber);
	    if(manageJoinFamilyCheck != null){
	       //*** 管理家里操作	
	       manageJoinFamilyResponse = ejlFamilyServiceImpl.manageFamily(request.getCtx(),userId, manageJoinFamilyCheck.getApplyUserId(), manageJoinFamilyCheck.getOptFamilyId(), manageType);
	       //*** 推送给相应用户
	       ejlFamilyServiceImpl.notifyForManageJoinFamily(manageJoinFamilyCheck.getOptFamilyId(), manageJoinFamilyCheck.getOldFamilyId(), userId, manageJoinFamilyCheck.getApplyUserId(), familyCode, manageType);
	    }
	    
      	fmlResponse.setData(manageJoinFamilyResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		
		return fmlResponse;
		}
	
}
