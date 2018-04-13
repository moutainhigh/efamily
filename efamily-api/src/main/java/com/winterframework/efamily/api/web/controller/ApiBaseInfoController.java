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

import com.winterframework.efamily.api.dto.Location;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IApiLocationService;
import com.winterframework.efamily.api.util.LocationUtil;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComUserService;

	
	@Controller("apiBaseInfoController")
	@RequestMapping("/api")
	public class ApiBaseInfoController{

		@Resource(name = "apiLocationServiceImpl")
		private IApiLocationService apiLocationService;
		@Resource(name = "ejlComUserServiceImpl")
		private IEjlComUserService ejlComUserService;
		@Resource(name="ejlComDeviceServiceImpl")
		private IEjlComDeviceService ejlComDeviceService;
		
		private static final Logger log = LoggerFactory.getLogger(ApiBaseInfoController.class);

		@RequestMapping("/phoneNumber")
		@ResponseBody
		protected String phoneNumber(HttpServletRequest request) throws Exception {
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
				String key = request.getParameter("key");
				String imei = request.getParameter("imei");
				if(StringUtils.isBlank(key) || StringUtils.isBlank(imei)){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}else{
					if(!key.equals("aaaaaaaabbbbbbbbccccccccdddddddd")){
						throw new BizException(ResultCode.KEY_INVALID.getCode());
					}
					if(imei.length()!=15){
						throw new BizException(ResultCode.IMEI_INVALID.getCode());
					}
					EjlDevice device=ejlComDeviceService.getByImei(imei);
					if(null==device){
						throw new BizException(ResultCode.IMEI_INVALID.getCode());
					}
					EjlUser user=ejlComUserService.get(device.getUserId());
					
					map.put("resultCode", "0");
					map.put("phoneNumber", user.getPhone());
				}				
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}
			return JsonUtils.toJson(map);
		}
}
