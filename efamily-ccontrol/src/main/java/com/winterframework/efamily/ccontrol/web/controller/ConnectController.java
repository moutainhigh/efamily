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
import com.winterframework.efamily.ccontrol.model.ResultCode;
import com.winterframework.efamily.ccontrol.service.IServerService;
import com.winterframework.efamily.dto.server.ServerRoute;
 
@Controller("connectController")
@RequestMapping("/server")
public class ConnectController {
	private static final Logger log = LoggerFactory.getLogger(ConnectController.class);
	@Resource(name = "serverServiceImpl")
	private IServerService serverService;
	
	@RequestMapping("/connect")
	@ResponseBody
	public Object connect(@RequestBody Request<ServerRoute> req) throws BizException{
		log.debug("context:"+req.getUserId()+" "+req.getDeviceId());
		Response<String> res=new Response<String>(req);  
		ServerRoute serverRoute=req.getData();
		try {
			if(null==serverRoute){
				throw new CcontrolException(ResultCode.PARAM_INVALID);
			}
			serverService.connect(serverRoute.getUserId(),serverRoute.getDeviceId(),serverRoute.getServerIp());
			res.setStatus(new Status(StatusCode.OK.getValue()));
			return res;
		} catch (CcontrolException e) {
			log.error("connect error.userId="+serverRoute.getUserId()+" deviceId="+serverRoute.getDeviceId()+" message:"+e.getMessage(),e);
			throw new BizException(e.getCode(),e.getMessage(),e);
		}
	}
	@RequestMapping("/disconnect")
	@ResponseBody
	public Object disconnect(@RequestBody Request<ServerRoute> req) throws BizException{
		log.debug("context:"+req.getUserId()+" "+req.getDeviceId());
		Response<String> res=new Response<String>(req);  
		ServerRoute serverRoute=req.getData();
		try {
			if(null==serverRoute){
				throw new CcontrolException(ResultCode.PARAM_INVALID);
			}
			serverService.disconnect(serverRoute.getUserId(),serverRoute.getDeviceId());
			res.setStatus(new Status(StatusCode.OK.getValue()));
			return res;
		} catch (CcontrolException e) {
			log.error("connect error.userId="+serverRoute.getUserId()+" deviceId="+serverRoute.getDeviceId()+" message:"+e.getMessage(),e);
			throw new BizException(e.getCode(),e.getMessage(),e);
		}
	}
	
}
