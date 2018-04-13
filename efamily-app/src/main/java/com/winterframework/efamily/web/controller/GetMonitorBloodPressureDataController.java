package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.GetMonitorBloodDataResponse;
import com.winterframework.efamily.dto.GetMonitorDataRequest;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlUserNotice;
import com.winterframework.efamily.service.IComEjlHealthBloodPressureService;
import com.winterframework.efamily.service.IEjlComUserNoticeService;
import com.winterframework.efamily.service.IEjlUserDeviceService;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("getMonitorBloodPressureDataController")
@RequestMapping("/server")
public class GetMonitorBloodPressureDataController {

	@Resource(name = "ejlComHealthBloodPressureServiceImpl")
	private IComEjlHealthBloodPressureService ejlComHealthBloodPressureServiceImpl;
	
	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService 	ejlUserDeviceServiceImpl;
	
	@Resource(name = "ejlComUserNoticeServiceImpl")
	private IEjlComUserNoticeService ejlComUserNoticeServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(GetMonitorBloodPressureDataController.class);
	@RequestMapping("/getMonitorBloodPressureData")
	@ResponseBody
	protected Response<GetMonitorBloodDataResponse> doHandle(@RequestBody Request<GetMonitorDataRequest> request) throws BizException {
		Response<GetMonitorBloodDataResponse> fmlResponse = new Response<GetMonitorBloodDataResponse>(request);
		GetMonitorBloodDataResponse getMonitorDataResponse = new GetMonitorBloodDataResponse();
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		try { 
			EjlHealthBloodPressure entity = new EjlHealthBloodPressure();
			entity.setUserId(request.getData().getUserId());
			entity.setDeviceId(request.getData().getDeviceId());
			entity.setFromTime(request.getData().getStartDateTime());
			entity.setToTime(request.getData().getEndDateTime());
			Context ctx = new Context();
			ctx.set("userId", request.getUserId());
			List<EjlHealthBloodPressure> list = ejlComHealthBloodPressureServiceImpl.selectListObjByAttribute(ctx, entity);
			List<Map<String,Object>> bloodList = new ArrayList<Map<String,Object>>();
			
			if(list != null){
				for(EjlHealthBloodPressure ejlHealthBloodPressure:list){
					Map<String,Object> ejlHealthBloodPressureMap = new HashMap<String,Object>();
					ejlHealthBloodPressureMap.put("systolicPressure", ejlHealthBloodPressure.getHigh());
					ejlHealthBloodPressureMap.put("diastolicPressure", ejlHealthBloodPressure.getLow());
					ejlHealthBloodPressureMap.put("heartRate", ejlHealthBloodPressure.getRate());
					ejlHealthBloodPressureMap.put("testTime", ejlHealthBloodPressure.getToTime());
					bloodList.add(ejlHealthBloodPressureMap);
				}
			}			
			getMonitorDataResponse.setBloodPressure(bloodList);
			EjlUserNotice entityQuery = new EjlUserNotice();
			entityQuery.setUserId(request.getUserId());
			entityQuery.setDeviceUserId(request.getData().getUserId());
			ctx.set("userId", request.getUserId());
			EjlUserNotice ejlUserNotice = ejlComUserNoticeServiceImpl.selectOneObjByAttribute(ctx, entityQuery);
			if(ejlUserNotice !=null){
				getMonitorDataResponse.setDiastolicRangeGt(ejlUserNotice.getDiastolicRangeGt()==null?0:Long.valueOf(ejlUserNotice.getDiastolicRangeGt()));
				getMonitorDataResponse.setDiastolicRangeLt(ejlUserNotice.getDiastolicRangeLt()==null?0:Long.valueOf(ejlUserNotice.getDiastolicRangeLt()));
				
				getMonitorDataResponse.setSystolicRangeGt(ejlUserNotice.getSystolicRangeGt()==null?0:Long.valueOf(ejlUserNotice.getSystolicRangeGt()));
				getMonitorDataResponse.setSystolicRangeLt(ejlUserNotice.getSystolicRangeLt()==null?0:Long.valueOf(ejlUserNotice.getSystolicRangeLt()));
			}
			fmlResponse.setData(getMonitorDataResponse);
			logger.info(mapper.writeValueAsString(getMonitorDataResponse));
		} catch (Exception e) {
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			logger.error("getMonitorBloodPressureData", e);
		}
		return fmlResponse;
	}
}
