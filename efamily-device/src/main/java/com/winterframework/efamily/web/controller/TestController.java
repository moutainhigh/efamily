package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.TestRequest;
import com.winterframework.efamily.dto.TestResponse;
import com.winterframework.efamily.service.IDeviceTestService;
 
@Controller("testController")
@RequestMapping("/device")
public class TestController {
	@Resource(name = "deviceTestServiceImpl")
	private IDeviceTestService deviceTestService;
 /*
	@RequestMapping(value = "/queryRelaxedServer")
	public Object hello(@RequestBody Request<GameServerQueryRequestByGame> request) {
		GameServerQueryRequestByGame data = request.getData();
		SysGameServer server = sysGameServerService.queryRelaxedServer(data.getGameId());
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setGameId(server.getGame());
		serverInfo.setIp(server.getIp());、
		serverInfo.setPort(server.getPort());
		return null;
	}*/
	
	@RequestMapping("/test")
	@ResponseBody
	public Object hello(@RequestBody Request<TestRequest> req) {
		TestRequest testReq=req.getData();
		Response<TestResponse> res=new Response<TestResponse>(req);
		
		TestResponse testRes=new TestResponse();
		deviceTestService.test(testReq.getId());
		testRes.setId(testReq.getId()+1);
		res.setData(testRes);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
