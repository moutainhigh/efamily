package com.winterframework.efamily.api.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.api.dto.HealthyMonitorDateSleepResponse;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IEjlHealthManageService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.JsonUtils;
	@Controller("deviceSleepController")
	@RequestMapping("/api")
	public class DeviceSleepController{

		@Resource(name = "ejlHealthManageServiceImpl")
		private IEjlHealthManageService ejlHealthManageServiceImpl;
		
		private static final Logger log = LoggerFactory.getLogger(DeviceSleepController.class);

		@RequestMapping("/sleep")
		@ResponseBody
		protected Object doHandle(HttpServletRequest request) throws Exception {
			String imei = request.getParameter("imei");
			String fromTime = request.getParameter("fromTime");
			String key = request.getParameter("key");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				if(StringUtils.isBlank(imei) || StringUtils.isBlank(key)||StringUtils.isBlank(fromTime)){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				
				if(imei.length()!=15){
					throw new BizException(ResultCode.IMEI_INVALID.getCode());
				}
				if(request.getParameter("fromTime").length()!=13){
					throw new BizException(ResultCode.TIME_INVALID.getCode());
				}
				
				Long time = null;
				try{
					time = Long.valueOf(request.getParameter("fromTime"));
				}catch(NumberFormatException e){
					log.error("number invalid.",e);
					throw new BizException(ResultCode.NUMBER_INVALID.getCode());
				}
				List<HealthyMonitorDateSleepResponse> healthyMonitorDateSleepResponse = ejlHealthManageServiceImpl.getMonitorDataSleepById(imei, time, null);
				map.put("resultCode", "0");
				map.put("imei", imei);
				map.put("sleep", healthyMonitorDateSleepResponse);
				
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}	
			return map;
		}
		
		@RequestMapping("/sleepBetweenTime")
		@ResponseBody
		protected Object sleepBetweenTime(HttpServletRequest request) throws Exception {
			String imei = request.getParameter("imei");
			String fromTimeStr = request.getParameter("fromTime");
			String endTimeStr = request.getParameter("toTime");
			String key = request.getParameter("key");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				if(StringUtils.isBlank(imei) || StringUtils.isBlank(key)||StringUtils.isBlank(fromTimeStr)||StringUtils.isBlank(endTimeStr)){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				if(imei.length()!=15){
					throw new BizException(ResultCode.IMEI_INVALID.getCode());
				}
				if(fromTimeStr.length()!=13||endTimeStr.length()!=13){
					throw new BizException(ResultCode.TIME_INVALID.getCode());
				}
				
				Long time = null;
				Long endTime = null;
				try{
					time = Long.valueOf(fromTimeStr);
					endTime = Long.valueOf(endTimeStr);
				}catch(NumberFormatException e){
					log.error("number invalid.",e);
					throw new BizException(ResultCode.NUMBER_INVALID.getCode());
				}
				List<HealthyMonitorDateSleepResponse> healthyMonitorDateSleepResponse = ejlHealthManageServiceImpl.getMonitorDataSleepById(imei, time, endTime);
				map.put("resultCode", "0");
				map.put("imei", imei);
				map.put("sleep", healthyMonitorDateSleepResponse);
				
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}	
			return map;
		}
		
		@RequestMapping("/sleepLast")
		@ResponseBody
		protected Object sleepLast(HttpServletRequest request) throws Exception {
			String imei = request.getParameter("imei");
			String key = request.getParameter("key");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				if(StringUtils.isBlank(imei) || StringUtils.isBlank(key)){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				
				if(imei.length()!=15){
					throw new BizException(ResultCode.IMEI_INVALID.getCode());
				}
				HealthyMonitorDateSleepResponse healthyMonitorDateSleepResponse = ejlHealthManageServiceImpl.getLastSleepBetweenTime(imei);
				map.put("resultCode", "0");
				map.put("imei", imei);
				map.put("sleep", healthyMonitorDateSleepResponse);
				
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}	
			return map;
		}
	}
