package com.winterframework.efamily.api.web.controller;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.winterframework.efamily.api.enums.ResultCode;




import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;




import com.winterframework.efamily.dto.ComOrgReq;
import com.winterframework.efamily.dto.WatchDeviceManageRequest;

import com.winterframework.efamily.dto.LocationOnff;
import com.winterframework.efamily.dto.device.param.DeviceParam;
import com.winterframework.efamily.entity.converter.ParameterConvertor.ParamIndex;

import com.winterframework.efamily.utils.HttpUtil;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Controller("institutionLocationOnffConfigController")
@RequestMapping("/api")
public class InstitutionLocationOnffConfigController {

	private static final Logger log = LoggerFactory.getLogger(InstitutionLocationOnffConfigController.class);

	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.batchSaveOrgDeviceConfig")
	private String urlPath;
	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;

	@RequestMapping(value = "locationOnff", method = RequestMethod.POST)
	@ResponseBody
	protected Object locationOnff(@RequestBody ComOrgReq<List<LocationOnff>> req,String key) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			List<LocationOnff> data = (List<LocationOnff>)req.getData();
			//String key = req.getKey();
			
			if (data == null) {
				throw new BizException(ResultCode.PARAM_EMPTY.getCode());
			}
			List<DeviceParam> list = new ArrayList<DeviceParam>();
			for(LocationOnff locationOnff:data){
				if(locationOnff.getImei()==null||locationOnff.getOnff()==null){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				
				if(locationOnff.getImei().length()!=15){
					throw new BizException(ResultCode.IMEI_INVALID.getCode());
				}
				DeviceParam dep = new DeviceParam();
				dep.setImei(locationOnff.getImei());
				WatchDeviceManageRequest common = new WatchDeviceManageRequest();
				common.setParameterIndex(String.valueOf(ParamIndex.LOCATION_ONFF.getValue()));
				common.setParameterContext(String.valueOf(locationOnff.getOnff()));
				dep.setParam(common);
				list.add(dep);
			}
			
			Context ctx = new Context();
			ctx.set("orgKey", key);
			ctx.set("userId", -1);
			Response<Object> bizRes = httpUtil.http(serverUrl,urlPath,ctx,list,Object.class);
			map.put("resultCode", bizRes.getStatus().getCode());
			map.put("errMsg", ResultCode.getResultCode(bizRes.getStatus().getCode()));
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
	
}
