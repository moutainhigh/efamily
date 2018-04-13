package com.winterframework.efamily.api.web.controller;

import java.util.ArrayList;
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

import com.winterframework.efamily.api.dto.DeviceHeartRateStruc;
import com.winterframework.efamily.api.dto.HealthyMonitorDateSleepResponse;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IEjlHealthHeartRateService;
import com.winterframework.efamily.api.service.IEjlHealthManageService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.service.IEjlComHealthHeartRateService;
	@Controller("deviceHeartController")
	@RequestMapping("/api")
	public class DeviceHeartController{

		@Resource(name = "ejlHealthHeartRateServiceImpl")
		private IEjlHealthHeartRateService ejlHealthHeartRateServiceImpl;
		
		private static final Logger log = LoggerFactory.getLogger(DeviceHeartController.class);

		@RequestMapping("/heartRate")
		@ResponseBody
		protected Object doHandle(HttpServletRequest request) throws Exception {
			String imei = request.getParameter("imei");
			String fromTime = request.getParameter("fromTime");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				String key = request.getParameter("key");
				
				
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
				
				List<EjlHealthHeartRate> list = ejlHealthHeartRateServiceImpl.getHealthHeartRateByTime(imei, time, null);
				if(null==list || list.isEmpty()){
					throw new BizException(ResultCode.HEART_NOT_EXIST.getCode());
				}
					List<DeviceHeartRateStruc> struts = new ArrayList<DeviceHeartRateStruc>();
					for(EjlHealthHeartRate ejlHealthHeartRate:list){
						DeviceHeartRateStruc  struc = new DeviceHeartRateStruc();
						struc.setCount(ejlHealthHeartRate.getRate());
						struc.setFromTime(ejlHealthHeartRate.getFromTime());
						struc.setToTime(ejlHealthHeartRate.getToTime());
						struts.add(struc);
					}
					map.put("resultCode", "0");
					map.put("heartRates", struts);
			
		}catch(BizException e){
			ResultCode resultCode=ResultCode.getResultCode(e.getCode());
			log.error(e.getCode()+" "+resultCode.getMsg(),e);
			map.put("resultCode", e.getCode()+"");
			map.put("errMsg", resultCode.getMsg());
		}
		map.put("imei", imei);
		return map;
	}
		
		
		@RequestMapping("/heartRateBetweenTime")
		@ResponseBody
		protected Object heartRateBetweenTime(HttpServletRequest request) throws Exception {
			String imei = request.getParameter("imei");
			String fromTimeStr = request.getParameter("fromTime");
			String endTimeStr = request.getParameter("toTime");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				String key = request.getParameter("key");
				
				
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
				
				List<EjlHealthHeartRate> list = ejlHealthHeartRateServiceImpl.getHealthHeartRateByTime(imei, time, endTime);
				if(null==list || list.isEmpty()){
					throw new BizException(ResultCode.HEART_NOT_EXIST.getCode());
				}
					List<DeviceHeartRateStruc> struts = new ArrayList<DeviceHeartRateStruc>();
					for(EjlHealthHeartRate ejlHealthHeartRate:list){
						DeviceHeartRateStruc  struc = new DeviceHeartRateStruc();
						struc.setCount(ejlHealthHeartRate.getRate());
						struc.setFromTime(ejlHealthHeartRate.getFromTime());
						struc.setToTime(ejlHealthHeartRate.getToTime());
						struts.add(struc);
					}
					map.put("resultCode", "0");
					map.put("heartRates", struts);
			
		}catch(BizException e){
			ResultCode resultCode=ResultCode.getResultCode(e.getCode());
			log.error(e.getCode()+" "+resultCode.getMsg(),e);
			map.put("resultCode", e.getCode()+"");
			map.put("errMsg", resultCode.getMsg());
		}
		map.put("imei", imei);
		return map;
	}
		
		@RequestMapping("/heartRateLast")
		@ResponseBody
		protected Object heartRateLast(HttpServletRequest request) throws Exception {
			String imei = request.getParameter("imei");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				String key = request.getParameter("key");
				
				
				if(StringUtils.isBlank(imei) || StringUtils.isBlank(key)){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				if(imei.length()!=15){
					throw new BizException(ResultCode.IMEI_INVALID.getCode());
				}
				
				
				EjlHealthHeartRate ejlHealthHeartRate = ejlHealthHeartRateServiceImpl.getLastUserHeartRate(imei);
				DeviceHeartRateStruc  struc = new DeviceHeartRateStruc();
				struc.setCount(ejlHealthHeartRate.getRate());
				struc.setFromTime(ejlHealthHeartRate.getFromTime());
				struc.setToTime(ejlHealthHeartRate.getToTime());
				map.put("resultCode", "0");
				map.put("heartRate", struc);
			
		}catch(BizException e){
			ResultCode resultCode=ResultCode.getResultCode(e.getCode());
			log.error(e.getCode()+" "+resultCode.getMsg(),e);
			map.put("resultCode", e.getCode()+"");
			map.put("errMsg", resultCode.getMsg());
		}catch(Exception e){
			map.put("resultCode", -1);
		}
		map.put("imei", imei);
		return map;
	}
	
}
