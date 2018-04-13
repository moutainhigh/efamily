package com.winterframework.efamily.web.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.winterframework.efamily.dto.SaveFamilyInfoRequest;
import com.winterframework.efamily.dto.SaveFamilyInfoResponse;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserService;

@Controller("saveFamilyInfoController")
@RequestMapping("/server")
public class SaveFamilyInfoController {

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;
	
	@Resource(name = "ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(SaveFamilyInfoController.class);

	@RequestMapping("/saveFamilyInfo")
	@ResponseBody
	protected Response<SaveFamilyInfoResponse> doHandle(@RequestBody Request<SaveFamilyInfoRequest> request) throws BizException {
		Response<SaveFamilyInfoResponse> fmlResponse = new Response<SaveFamilyInfoResponse>(request);
		SaveFamilyInfoResponse saveFamilyInfoResponse = new SaveFamilyInfoResponse();
		
		//*** 判断当前操作人是否家庭群主
		if(ejlFamilyUserServiceImpl.checkIsFamilyHost(request.getUserId(), request.getData().getFamilyId())){
			Long familyId = null;
			EjlFamily family = new EjlFamily();
			family.setId(request.getData().getFamilyId());
			family.setName(request.getData().getFamilyName());
			family.setHeadImg(request.getData().getHeadImgUrl());
			familyId = ejlFamilyServiceImpl.saveOrUpdateFamily(request.getCtx(),family,request.getUserId());
			
			//********* 信息修改推送   ********************** 
			EjlFamily familyQuery = ejlFamilyServiceImpl.get(familyId);
			Map<String, String> paramMap = new HashMap<String,String>();
	      	paramMap.put("familyId", familyQuery.getId()+"");
	      	paramMap.put("familyName", familyQuery.getName());
	      	paramMap.put("familyCode", familyQuery.getCode());
	      	paramMap.put("headImgUrl", familyQuery.getHeadImg());
	      	ejlUserServiceImpl.notifyForFamilyInfoUpdate(paramMap, request.getUserId(),familyId);
	      	
			saveFamilyInfoResponse.setFamilyId(familyId);
			fmlResponse.setData(saveFamilyInfoResponse);
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		}else{
			logger.error("用户不是家庭群主，不允许修改家庭信息，操作失败。 request.getData().getFamilyId() = "+request.getData().getFamilyId()+" ; userId = "+request.getUserId());
			throw new BizException(StatusBizCode.USER_UN_FAMILY_HOST.getValue());
		}
		
		return fmlResponse;
	}
}