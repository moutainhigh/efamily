package com.winterframework.efamily.api.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.api.dto.Location;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IApiLocationService;
import com.winterframework.efamily.api.util.LocationUtil;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.ComOrgReq;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComUserService;


	
	@Controller("apiLocationController")
	@RequestMapping("/api")
	public class ApiLocationController{

		@Resource(name = "apiLocationServiceImpl")
		private IApiLocationService apiLocationService;
		@Resource(name = "ejlComUserServiceImpl")
		private IEjlComUserService ejlComUserService;
		@Resource(name="ejlComDeviceServiceImpl")
		private IEjlComDeviceService ejlComDeviceService;
		
		private static final Logger log = LoggerFactory.getLogger(ApiLocationController.class);

		@RequestMapping("/location")
		@ResponseBody
		protected Object location(HttpServletRequest request) throws Exception {
			String imei = request.getParameter("imei");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			if(StringUtils.isBlank(imei)){
				map.put("resultCode", "100001");
				map.put("errMsg", "parameter imei is empty.");
			}else{
				EjlDevice device=ejlComDeviceService.getByImei(imei);
				if(device!=null && device.getUserId()!=null && device.getUserId().intValue()>0){
					Date time=DateUtils.addHours(DateUtils.currentDate(), -6);
					EjlLocation ejlLocation=apiLocationService.getUserLatestLocation(device.getUserId(), device.getId(),YesNo.YES.getValue(),time);
					if(null==ejlLocation){
						map.put("resultCode", ResultCode.DATA_NOT_EXIST.getCode());
						map.put("errMsg", "location not exists.");
					}else{
						map.put("resultCode", "0");
						map.put("location", ejlLocation.getLocation().split(",")[1]+","+ejlLocation.getLocation().split(",")[0]);
						//map.put("imei", imei);
						map.put("time",ejlLocation.getTime().getTime()+"");
						map.put("timeStay",ejlLocation.getTimeStay()+"");
					}
				}else{
					map.put("resultCode", ResultCode.DEVICE_NOT_BIND.getCode());
					map.put("errMsg", "device not bind.");
				}
			}
			
			return map;
		}
		
		@RequestMapping(value = "locationBatch", method = RequestMethod.POST)
		@ResponseBody
		protected Object location(@RequestBody ComOrgReq<List<String>> req,String key) throws Exception {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			try {
				List<String> data = (List<String>)req.getData();
				//String key = req.getKey();
				
				if (data == null) {
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				for(String imei:data){
					if(imei == null || imei.length()!=15){
						throw new BizException(ResultCode.IMEI_INVALID.getCode());
					}
					EjlDevice device=ejlComDeviceService.getByImei(imei);
					
					Date time=DateUtils.addHours(DateUtils.currentDate(), -6);
					EjlLocation ejlLocation=apiLocationService.getUserLatestLocation(device.getUserId(), device.getId(),YesNo.YES.getValue(),time);
					if(ejlLocation != null){
						Map<String,Object> mapr = new LinkedHashMap<String,Object>();
						mapr.put("location", ejlLocation.getLocation().split(",")[1]+","+ejlLocation.getLocation().split(",")[0]);
						mapr.put("time",ejlLocation.getTime().getTime()+"");
						mapr.put("timeStay",ejlLocation.getTimeStay()+"");
						mapr.put("imei", imei);
						list.add(mapr);
					}
				}
				map.put("resultCode", 0);
				map.put("errMsg", ResultCode.getResultCode(0));
				map.put("data", list);
			} catch (BizException e) {
				ResultCode resultCode = ResultCode.getResultCode(e.getCode());
				log.error(e.getCode() + " " + resultCode.getMsg(), e);
				map.put("resultCode", e.getCode() + "");
				map.put("errMsg", resultCode.getMsg());
			} catch (Exception e1) {
				map.put("resultCode", "-1");
				map.put("errMsg", "unknown error.");
				log.error(e1.getMessage());
			}
			return map;	
		}
		
		@RequestMapping("/locationByTime")
		@ResponseBody
		protected Object locationByTime(HttpServletRequest request) throws Exception {
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				String key = request.getParameter("key");
				String imei = request.getParameter("imei");
				String bizTimeStr = request.getParameter("time");
			
				if(null==key || null==imei || null==bizTimeStr){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}else{
					if(imei.length()!=15){
						throw new BizException(ResultCode.IMEI_INVALID.getCode());
					}
					if(bizTimeStr.length()!=13){
						throw new BizException(ResultCode.TIME_INVALID.getCode());
					}
					
					Long bizTime=null;
					try{
						bizTime=Long.valueOf(bizTimeStr);
					}catch(NumberFormatException e){
						log.error("number invalid.",e);
						throw new BizException(ResultCode.NUMBER_INVALID.getCode());
					}
					
					Context ctx=new Context();
					List<EjlLocation> locationList=apiLocationService.getListAfterByImeiAndTime(ctx, imei,DateUtils.getDate(bizTime));
					List<Location> lcList=new ArrayList<Location>();	
					final int limitCount=1000;	//一次请求上限记录数
					if(null!=locationList){
						for(EjlLocation location:locationList){
							Location lc=new Location();
							lc.setLongitude(LocationUtil.getLogitude(location.getLocation()));
							lc.setLatitude(LocationUtil.getLatitude(location.getLocation()));
							lc.setTime(location.getTime().getTime());
							lc.setTimeStay(location.getTimeStay());
							lcList.add(lc);	
							
							if(lcList.size()==limitCount) break;
						}
					}
					map.put("resultCode", "0");
					map.put("imei", imei);
					map.put("locationList",lcList);
				}
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}
			return map;
		}
		
		
		
		@RequestMapping("/locationBetweenTime")
		@ResponseBody
		protected Object locationBetweenTime(HttpServletRequest request) throws Exception {
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				String key = request.getParameter("key");
				String imei = request.getParameter("imei");
				String startTimeStr = request.getParameter("fromTime");
				String endTimeStr = request.getParameter("toTime");
			
				if(null==key || null==imei || null==startTimeStr || null==endTimeStr){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}else{
					if(imei.length()!=15){
						throw new BizException(ResultCode.IMEI_INVALID.getCode());
					}
					if(startTimeStr.length()!=13 || endTimeStr.length()!=13){
						throw new BizException(ResultCode.TIME_INVALID.getCode());
					}
					
					Long startTime=null;
					Long endTime = null;
					try{
						startTime=Long.valueOf(startTimeStr);
						endTime = Long.valueOf(endTimeStr);
					}catch(NumberFormatException e){
						log.error("number invalid.",e);
						throw new BizException(ResultCode.NUMBER_INVALID.getCode());
					}
					
					Context ctx=new Context();
					List<EjlLocation> locationList=apiLocationService.getListBetweenByDeviceIdAndTime(ctx, imei,DateUtils.getDate(startTime),DateUtils.getDate(endTime));
					List<Location> lcList=new ArrayList<Location>();	
					final int limitCount=1000;	//一次请求上限记录数
					if(null!=locationList){
						for(EjlLocation location:locationList){
							Location lc=new Location();
							lc.setLongitude(LocationUtil.getLogitude(location.getLocation()));
							lc.setLatitude(LocationUtil.getLatitude(location.getLocation()));
							lc.setTime(location.getTime().getTime());
							lc.setTimeStay(location.getTimeStay());
							lcList.add(lc);	
							
							if(lcList.size()==limitCount) break;
						}
					}
					map.put("resultCode", "0");
					map.put("imei", imei);
					map.put("locationList",lcList);
				}
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}
			return map;
		}
}
