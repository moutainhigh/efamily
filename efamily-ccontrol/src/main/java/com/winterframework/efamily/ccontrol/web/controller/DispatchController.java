package com.winterframework.efamily.ccontrol.web.controller;

import java.util.List;

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
import com.winterframework.efamily.ccontrol.exception.CcontrolException;
import com.winterframework.efamily.ccontrol.service.IServerService;
import com.winterframework.efamily.dto.server.ServerLoad;
 
@Controller("serverController")
@RequestMapping("/server")
public class DispatchController {
	private static final Logger log = LoggerFactory.getLogger(DispatchController.class);
	@Resource(name = "serverServiceImpl")
	private IServerService serverService;
	
	@RequestMapping("/load")
	@ResponseBody
	public Object load(@RequestBody Request<String> req) throws BizException{
		try {
			Response<List<ServerLoad>> res=new Response<List<ServerLoad>>(req);  
			List<ServerLoad> serverLoadList=serverService.load();
			res.setStatus(new Status(StatusCode.OK.getValue()));
			res.setData(serverLoadList);
			return res;
		} catch (CcontrolException e) {
			log.error("connect error.userId="+req.getUserId()+" deviceId="+req.getDeviceId()+" message:"+e.getMessage(),e);
			throw new BizException(e.getCode(),e.getMessage(),e);
		}
	}
}
