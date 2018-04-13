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

import com.winterframework.efamily.api.dto.DeviceSportsStruc;
import com.winterframework.efamily.api.dto.HealthyMonitorDateSleepResponse;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IEjlHealthManageService;
import com.winterframework.efamily.api.service.IEjlHealthStepCountService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EjlHealthStepCount;
import com.winterframework.efamily.service.IEjlComHealthStepCountService;
	@Controller("deviceSportsController")
	@RequestMapping("/api")
	public class DeviceSportsController{

		@Resource(name = "ejlHealthStepCountServiceImpl")
		private IEjlHealthStepCountService ejlHealthStepCountServiceImpl;
		
		private static final Logger log = LoggerFactory.getLogger(DeviceSportsController.class);

		@RequestMapping("/sport")
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
				List<EjlHealthStepCount> list = ejlHealthStepCountServiceImpl.getHealthStepCountByTime(imei, time, null);
				if(null==list || list.isEmpty()){
					throw new BizException(ResultCode.SPORTS_NOT_EXIST.getCode());
				}else{
					List<DeviceSportsStruc> strucs = new ArrayList<DeviceSportsStruc>();
					for(EjlHealthStepCount count :list){
						DeviceSportsStruc struc = new DeviceSportsStruc();
						struc.setFromTime(DateUtils.convertDate2Long(count.getBegintime()));
						struc.setToTime(DateUtils.convertDate2Long(count.getEndtime()));
						struc.setMode(count.getType());
						struc.setCount(count.getType().intValue()==3?count.getHeight():count.getSteps());
						strucs.add(struc);
					}
					map.put("resultCode", "0");
					map.put("sports", strucs);
				
			}}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}
			map.put("imei", imei);
			return map;
		}
		
		
		@RequestMapping("/sportBetweenTime")
		@ResponseBody
		protected Object sportBetweenTime(HttpServletRequest request) throws Exception {
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
				List<EjlHealthStepCount> list = ejlHealthStepCountServiceImpl.getHealthStepCountByTime(imei, time, endTime);
				if(null==list || list.isEmpty()){
					throw new BizException(ResultCode.SPORTS_NOT_EXIST.getCode());
				}else{
					List<DeviceSportsStruc> strucs = new ArrayList<DeviceSportsStruc>();
					for(EjlHealthStepCount count :list){
						DeviceSportsStruc struc = new DeviceSportsStruc();
						struc.setFromTime(DateUtils.convertDate2Long(count.getBegintime()));
						struc.setToTime(DateUtils.convertDate2Long(count.getEndtime()));
						struc.setMode(count.getType());
						struc.setCount(count.getType().intValue()==3?count.getHeight():count.getSteps());
						strucs.add(struc);
					}
					map.put("resultCode", "0");
					map.put("sports", strucs);
				
			}}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}
			map.put("imei", imei);
			return map;
		}
		
		
		
		@RequestMapping("/sportLast")
		@ResponseBody
		protected Object sportLast(HttpServletRequest request) throws Exception {
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
			
			
			
				EjlHealthStepCount count = ejlHealthStepCountServiceImpl.getLastHealthStepCountByTime(imei);
				if(null==count){
					throw new BizException(ResultCode.SPORTS_NOT_EXIST.getCode());
				}else{
					DeviceSportsStruc struc = new DeviceSportsStruc();
					struc.setFromTime(DateUtils.convertDate2Long(count.getBegintime()));
					struc.setToTime(DateUtils.convertDate2Long(count.getEndtime()));
					struc.setMode(count.getType());
					struc.setCount(count.getType().intValue()==3?count.getHeight():count.getSteps());
					
					map.put("resultCode", "0");
					map.put("sports", struc);
				
			}}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}
			map.put("imei", imei);
			return map;
		}
		
	}
