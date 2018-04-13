package com.winterframework.efamily.ccontrol.web.controller;

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
 
@Controller("routeController")
@RequestMapping("/server")
public class RouteController {
	private static final Logger log = LoggerFactory.getLogger(RouteController.class);
	@Resource(name = "serverServiceImpl")
	private IServerService serverService;
	
	@RequestMapping("/route")
	@ResponseBody
	public Object route(@RequestBody Request<String> req) throws BizException{
		try {
			Response<String> res=new Response<String>(req);  
			String serverIp=serverService.route(req.getUserId(),req.getDeviceId());
			res.setStatus(new Status(StatusCode.OK.getValue()));
			res.setData(serverIp);
			return res;
		} catch (CcontrolException e) {
			log.error("route error.userId="+req.getUserId()+" deviceId="+req.getDeviceId()+" message:"+e.getMessage(),e);
			throw new BizException(e.getCode(),e.getMessage(),e);
		}
	}
	@RequestMapping("/isConnected")
	@ResponseBody
	public Object isConnected(@RequestBody Request<String> req) throws BizException{
		try {
			Response<Boolean> res=new Response<Boolean>(req);  
			boolean isConnected=serverService.isConnected(req.getUserId(),req.getDeviceId());
			res.setStatus(new Status(StatusCode.OK.getValue()));
			res.setData(isConnected);
			return res;
		} catch (CcontrolException e) {
			log.error("route error.userId="+req.getUserId()+" deviceId="+req.getDeviceId()+" message:"+e.getMessage(),e);
			throw new BizException(e.getCode(),e.getMessage(),e);
		}
	}
}
