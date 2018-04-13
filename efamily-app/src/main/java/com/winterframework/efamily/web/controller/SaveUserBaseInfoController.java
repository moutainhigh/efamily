package com.winterframework.efamily.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.winterframework.efamily.dto.SaveUserBaseInfoRequest;
import com.winterframework.efamily.dto.SaveUserBaseInfoResponse;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlFileUploadService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 保存用户基本信息handler
 * @author floy
 *
 */
@Controller("saveUserBaseInfoController")
@RequestMapping("/server")
public class SaveUserBaseInfoController  {

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@Resource(name = "ejlFileUploadServiceImpl")
	private IEjlFileUploadService ejlFileUploadServiceImpl;

/*
	@Resource(name="noticeServiceImpl")
	private INoticeService noticeServiceImpl;*/
	
	private static final Logger logger = LoggerFactory.getLogger(SaveUserBaseInfoController.class);

	@RequestMapping("/saveUserBaseInfo")
	@ResponseBody
	protected Response<SaveUserBaseInfoResponse> doHandle(@RequestBody Request<SaveUserBaseInfoRequest> request) throws BizException {
		Response<SaveUserBaseInfoResponse> fmlResponse = new Response<SaveUserBaseInfoResponse>(request);
		SaveUserBaseInfoResponse saveUserBaseInfoResponse = new SaveUserBaseInfoResponse();
			EjlUser user = ejlUserServiceImpl.get(request.getData().getUserId()); 
			if(null==user){
				throw new BizException(StatusBizCode.USER_UN_VALID.getValue());
			}
			
			if(request.getData().getAge()!=null){
				user.setAge(request.getData().getAge());
			}
			if(request.getData().getImage()!=null){
				user.setHeadImg(request.getData().getImage()); 
			}
			if(request.getData().getNickName()!=null){
				user.setNickName(request.getData().getNickName());
			}
			if(request.getData().getSex()!=null){
				user.setSex(request.getData().getSex());
			}
			if(request.getData().getUserName()!=null){
				user.setUserName(request.getData().getUserName());
			}
			if(request.getData().getSignature()!=null){
				user.setSignature(request.getData().getSignature());
			}
			if(request.getData().getPhone()!=null){
				if(ejlUserServiceImpl.getUserByPhone(request.getData().getPhone())!=null){
					logger.error("用户手机号码已经存在，保存用户信息失败：phone = "+request.getData().getPhone()+" ; userId = "+request.getUserId());
					throw new BizException(StatusBizCode.USER_PHONE_EXIST.getValue());
				}
				user.setPhone(request.getData().getPhone());
			}
			
			ejlUserServiceImpl.saveOrUpdateUser(request.getCtx(),user);
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			
			saveUserBaseInfoResponse.setImage(user.getHeadImg());
			fmlResponse.setData(saveUserBaseInfoResponse);
			/**
          	｛userid(改变信息的用户ID)，icon(头像)，
          	 siganature( 签名)，sex(性别)，nickName(备注名称)，userType(用户类型)｝
          	 */
			if(StringUtils.isNotBlank(request.getData().getImage())
					||StringUtils.isNotBlank(request.getData().getUserName())
					||StringUtils.isNotBlank(request.getData().getNickName())
					||request.getData().getSex()!=null
					||StringUtils.isNotBlank(request.getData().getSignature())){
				
				EjlUser userResult = ejlUserServiceImpl.get(user.getId());
				Map<String, String> paramMap = new HashMap<String,String>();
	          	paramMap.put("userId", String.valueOf(userResult.getId()));
	          	paramMap.put("icon", userResult.getHeadImg());
	          	paramMap.put("signature", userResult.getSignature());
	          	paramMap.put("nickName", userResult.getNickName());
	          	paramMap.put("sex", String.valueOf(userResult.getSex()));
	          	paramMap.put("userType", String.valueOf(userResult.getType()));
	          	ejlUserServiceImpl.notifyForUpdateUserInfo(paramMap, user.getId(),request.getUserId());
			}else{
				logger.error("用户信息修改，此次修改的内容不需要推送。 userId = "+request.getUserId()+" ; modifyUserId = "+request.getData().getUserId());
			}
          
		return fmlResponse;
	}
	
	
}
