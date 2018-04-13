package com.winterframework.efamily.server.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.server.web.result.ServerIpResult;
import com.winterframework.modules.spring.exetend.PropertyConfig;

	
@Controller("serverController")
@RequestMapping("/device")
public class ServerController{
	private static final Logger log = LoggerFactory.getLogger(ServerController.class);
	
	@PropertyConfig("netty.ip.mod0")
	private String ipmod0; 
	
	@RequestMapping(value="/ip")
	@ResponseBody
	protected Object doHandle(HttpServletRequest request) throws Exception {
		String key = request.getParameter("key");
		ServerIpResult result=new ServerIpResult();
		if(!"11111111222222223333333344444444".equals(key)){
			result.setStatus(10001);
			result.setErrMsg("key is invalid.");
		}else{
			result.setStatus(StatusCode.OK.getValue());
			result.setIp(ipmod0);
		}
		
		return result;
	}
}
