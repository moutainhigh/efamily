package com.winterframework.efamily.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.device.DeviceLunarRequest;
import com.winterframework.efamily.entity.EfLunar;
import com.winterframework.efamily.service.IEfComLunarService;


@Controller("deviceLunarQueryController")
@RequestMapping("/device/lunar")
public class DeviceLunarQueryController {
	private static final Logger logger = LoggerFactory.getLogger(DeviceLunarQueryController.class);
	@Resource(name = "ejlComLunarServiceImpl")
	private IEfComLunarService ejlComLunarServiceImpl; 
	
	@RequestMapping("/get")
	@ResponseBody
	public Response<DeviceLunarRequest> get(@RequestBody Request<Long> request) throws Exception{
		Response<DeviceLunarRequest> fmlResponse = new Response<DeviceLunarRequest>(request);
		DeviceLunarRequest struc = new DeviceLunarRequest();
		Long time = request.getData();
		Date date = DateUtils.parse(DateUtils.format(DateUtils.convertLong2Date(time)));
		try{
		EfLunar efLunar = ejlComLunarServiceImpl.getEfLunarByDate(date);
		if(efLunar != null){
		struc.setDate(DateUtils.format(efLunar.getSolarDate()));
		struc.setFitavoid(efLunar.getSuit() + "-" + efLunar.getAvoid());
		struc.setGanzhi(efLunar.getGanzhi());
		struc.setLunar(efLunar.getLunarDate());struc.setWeek(efLunar.getWeek()+"");
		struc.setZodiac(efLunar.getZodiac());
		struc.setWeek(efLunar.getWeek()==1?"星期一":efLunar.getWeek()==2?"星期二":efLunar.getWeek()==3?"星期三":
				efLunar.getWeek()==4?"星期四":efLunar.getWeek()==5?"星期五":efLunar.getWeek()==6?"星期六":
					efLunar.getWeek()==7?"星期日":null);
		fmlResponse.setData(struc);
		}
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		}catch(Exception e){
			logger.error("get device user info error",e);
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		return fmlResponse;
	}
	
	
	@RequestMapping("/getLunar")
	@ResponseBody
	public Response<DeviceLunarRequest> getLunar(@RequestBody Request<String> request) throws Exception{
		Response<DeviceLunarRequest> fmlResponse = new Response<DeviceLunarRequest>(request);
		DeviceLunarRequest struc = new DeviceLunarRequest();
		Date date = DateUtils.parse(request.getData());
		try{
		EfLunar efLunar = ejlComLunarServiceImpl.getEfLunarByDate(date);
		if(efLunar != null){
		struc.setDate(DateUtils.format(efLunar.getSolarDate()));
		struc.setFitavoid(efLunar.getSuit() + "-" + efLunar.getAvoid());
		struc.setGanzhi(efLunar.getGanzhi());
		struc.setLunar(efLunar.getLunarDate());struc.setWeek(efLunar.getWeek()+"");
		struc.setZodiac(efLunar.getZodiac());
		struc.setWeek(efLunar.getWeek()==1?"星期一":efLunar.getWeek()==2?"星期二":efLunar.getWeek()==3?"星期三":
				efLunar.getWeek()==4?"星期四":efLunar.getWeek()==5?"星期五":efLunar.getWeek()==6?"星期六":
					efLunar.getWeek()==7?"星期日":null);
		fmlResponse.setData(struc);
		}
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		}catch(Exception e){
			logger.error("get device getLunar error",e);
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		return fmlResponse;
	}
}
