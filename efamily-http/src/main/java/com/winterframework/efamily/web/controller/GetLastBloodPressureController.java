package com.winterframework.efamily.web.controller;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.modules.spring.exetend.PropertyConfig;

import com.winterframework.efamily.result.BaseHttpResult;
import com.winterframework.efamily.utils.HttpUtil;


	
@Controller("getLastBloodPressureController")
@RequestMapping("/server/blood")
public class GetLastBloodPressureController{
	private static final Logger log = LoggerFactory.getLogger(GetLastBloodPressureController.class);
		
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.getLastBlood")
	private String urlPath;
	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;

	@RequestMapping("/lastBlood")
	@ResponseBody
	protected Object lastBlood(HttpServletRequest request) throws Exception {
		BaseHttpResult reslt = new BaseHttpResult();
		try{
			String userId = request.getParameter("userId");
			String deviceId = request.getParameter("deviceId");
			if(deviceId == null){
				reslt.setResultCode(StatusCode.PARAM_INVALID.getValue());
				return reslt;
			}
			Map<String,Long> data = new HashMap<String,Long>();
			data.put("deviceId", Long.valueOf(deviceId));
			if(userId!=null){
				data.put("userId", Long.valueOf(userId));
			}
			Response<Map<String,Object>> bizRes = httpUtil.http(serverUrl,urlPath,data, Map.class);
			if(bizRes.getStatus().getCode()==0){
				reslt.setData(bizRes.getData());
			}
			reslt.setResultCode(bizRes.getStatus().getCode());
		}catch(BizException e){
			reslt.setResultCode(StatusCode.UNKNOW.getValue());
		}
		return reslt;
	}
}
