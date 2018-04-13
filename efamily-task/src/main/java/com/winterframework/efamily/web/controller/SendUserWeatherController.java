package com.winterframework.efamily.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IWeatherService;

@Controller("sendUserWeatherController")
@RequestMapping("/task/weather")
public class SendUserWeatherController {
	private Logger log = LoggerFactory.getLogger(SendUserWeatherController.class);

	@Resource(name = "weatherServiceImpl")
	private IWeatherService weatherService; 
	
	
	@RequestMapping("/sendUserWeather")
	@ResponseBody
	protected Response<Object> sendUserWeather(@RequestBody Request<Map<String,Long>> request) throws Exception {
		log.debug("reset send user Weather begin userId="+request.getData().get("userId"));
		Response<Object> resp = new Response<Object>(request);
		Context ctx = new Context();
		ctx.set("userId", request.getData().get("userId"));
		ctx.set("deviceId", request.getData().get("deviceId"));
		weatherService.sendUserCityWeather(ctx);
		resp.setStatus(new Status(StatusBizCode.OK.getValue()));
		log.debug("reset send user Weather end userId="+request.getData().get("userId"));
		return resp;
	}
}
