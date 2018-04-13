package com.winterframework.efamily.web.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.service.IComEjlHealthBloodPressureService;

@Controller("GetLastBloodPressureController")
@RequestMapping("/server")
public class GetLastBloodPressureController {

	@Resource(name = "ejlComHealthBloodPressureServiceImpl")
	private IComEjlHealthBloodPressureService ejlComHealthBloodPressureServiceImpl;
	
	
	private static final Logger logger = LoggerFactory.getLogger(GetLastBloodPressureController.class);
	
	@RequestMapping("/getLastBlood")
	@ResponseBody
	protected Response<Map<String,Object>> getLastBlood(@RequestBody Request<Map<String,Long>> request) throws BizException {
		Long userId = request.getData().get("userId");
		Long deviceId = request.getData().get("deviceId");
		Response<Map<String,Object>> fmlResponse = new Response<Map<String,Object>>(request);
		try{
			EjlHealthBloodPressure ejlHealthBloodPressure = new EjlHealthBloodPressure();
			ejlHealthBloodPressure.setUserId(userId);
			ejlHealthBloodPressure.setDeviceId(deviceId);
			ejlHealthBloodPressure.setFromTime(DateUtils.convertDate2Long(DateUtils.addDays(DateUtils.currentDate(), -7)));

			EjlHealthBloodPressure bloodPressure=ejlComHealthBloodPressureServiceImpl.getLastBloodPressure(ejlHealthBloodPressure);
			if(bloodPressure != null){
				Map<String,Object> ejlHealthBloodPressureMap = new HashMap<String,Object>();
				ejlHealthBloodPressureMap.put("systolicBloodPressure", bloodPressure.getHigh());
				ejlHealthBloodPressureMap.put("diastolicBloodPressure", bloodPressure.getLow());
				ejlHealthBloodPressureMap.put("pulse", bloodPressure.getRate());
				ejlHealthBloodPressureMap.put("time", bloodPressure.getToTime());
				fmlResponse.setData(ejlHealthBloodPressureMap);
			}
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		}catch(Exception e){
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			logger.error("getLastBlood", e);
		}
		return fmlResponse;
	}
	
}
