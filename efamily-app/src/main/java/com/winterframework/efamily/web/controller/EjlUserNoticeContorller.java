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
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.UserNoticeStrucRequest;
import com.winterframework.efamily.dto.UserNoticeStrucResponse;
import com.winterframework.efamily.entity.EjlUserNotice;

import com.winterframework.efamily.service.IEjlComUserNoticeService;

@Controller("ejlUserNoticeContorller")
@RequestMapping("/server")
public class EjlUserNoticeContorller {

	private static final Logger log = LoggerFactory.getLogger(EjlUserNoticeContorller.class);
	@Resource(name = "ejlComUserNoticeServiceImpl")
	private IEjlComUserNoticeService ejlComUserNoticeServiceImpl;

	@RequestMapping("/getEjlUserNotice")
	@ResponseBody
	protected Response<UserNoticeStrucResponse> getEjlUserNotice(@RequestBody Request<UserNoticeStrucRequest> request)throws BizException{
		Response<UserNoticeStrucResponse> fmlResponse = new Response<UserNoticeStrucResponse>(request);
		UserNoticeStrucResponse userNoticeStrucResponse = new UserNoticeStrucResponse();
		userNoticeStrucResponse.setUserId(request.getUserId());
		userNoticeStrucResponse.setDeviceUserId(request.getData().getDeviceUserId());
		EjlUserNotice queryEntity = new EjlUserNotice();
		queryEntity.setUserId(request.getUserId());
		queryEntity.setDeviceUserId(request.getData().getDeviceUserId());
		Context ctx = new Context();
		ctx.set("userId", request.getUserId());
		EjlUserNotice ejlUserNotice = ejlComUserNoticeServiceImpl.selectOneObjByAttribute(ctx, queryEntity);
		
		if(ejlUserNotice != null){
			userNoticeStrucResponse.setBloodStatus(ejlUserNotice.getBloodStatus());
			userNoticeStrucResponse.setDiastolicRangeGt(ejlUserNotice.getDiastolicRangeGt());
			userNoticeStrucResponse.setDiastolicRangeLt(ejlUserNotice.getDiastolicRangeLt());
			userNoticeStrucResponse.setRateRangeGt(ejlUserNotice.getRateRangeGt());
			userNoticeStrucResponse.setRateRangeLt(ejlUserNotice.getRateRangeLt());
			userNoticeStrucResponse.setRateStatus(ejlUserNotice.getRateStatus());
			userNoticeStrucResponse.setSystolicRangeGt(ejlUserNotice.getSystolicRangeGt());
			userNoticeStrucResponse.setSystolicRangeLt(ejlUserNotice.getSystolicRangeLt());
			userNoticeStrucResponse.setId(ejlUserNotice.getId());
		}
		fmlResponse.setData(userNoticeStrucResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
	
	@RequestMapping("/saveEjlUserNotice")
	@ResponseBody
	protected Response<Object> saveEjlUserNotice(@RequestBody Request<UserNoticeStrucRequest> request)throws BizException{
		Response<Object> fmlResponse = new Response<Object>(request);
		EjlUserNotice entity = new EjlUserNotice();
		entity.setId(request.getData().getId());
		entity.setUserId(request.getUserId());
		entity.setDeviceUserId(request.getData().getDeviceUserId());
		entity.setBloodStatus(request.getData().getBloodStatus());
		entity.setDiastolicRangeGt(request.getData().getDiastolicRangeGt());
		entity.setDiastolicRangeLt(request.getData().getDiastolicRangeLt());
		entity.setRateRangeGt(request.getData().getRateRangeGt());
		entity.setRateRangeLt(request.getData().getRateRangeLt());
		entity.setRateStatus(request.getData().getRateStatus());
		entity.setSystolicRangeGt(request.getData().getSystolicRangeGt());
		entity.setSystolicRangeLt(request.getData().getSystolicRangeLt());
		Context ctx = new Context();
		ctx.set("userId", request.getUserId());
		ejlComUserNoticeServiceImpl.save(ctx, entity);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
