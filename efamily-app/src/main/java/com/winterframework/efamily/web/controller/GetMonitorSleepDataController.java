package com.winterframework.efamily.web.controller;

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
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.GetMonitorDataRequest;
import com.winterframework.efamily.dto.GetMonitorDataResponse;
import com.winterframework.efamily.dto.HealthyMonitorDateSleepResponse;
import com.winterframework.efamily.dto.QueryMonitorDataRequest;
import com.winterframework.efamily.service.IEjlHealthManageService;
import com.winterframework.efamily.service.IEjlUserDeviceService;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("getMonitorSleepDataController")
@RequestMapping("/server")
public class GetMonitorSleepDataController {
	private static final Logger logger = LoggerFactory.getLogger(GetMonitorSleepDataController.class);

	@Resource(name = "ejlHealthManageServiceImpl")
	private IEjlHealthManageService ejlHealthManageServiceImpl;
	
	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService 	ejlUserDeviceServiceImpl;

	@RequestMapping("/getMonitorSleepData")
	@ResponseBody
	protected Response<GetMonitorDataResponse> doHandle(@RequestBody Request<GetMonitorDataRequest> request) throws BizException {
		Response<GetMonitorDataResponse> fmlResponse = new Response<GetMonitorDataResponse>(request);
		GetMonitorDataResponse getMonitorDataResponse = new GetMonitorDataResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		QueryMonitorDataRequest queryRequest = new QueryMonitorDataRequest();
		try {
			queryRequest.setDateType(request.getData().getDateType()==null?null:request.getData().getDateType()); 
			queryRequest.setStartDateTime(request.getData().getStartDateTime()==null?null:request.getData().getStartDateTime());
			queryRequest.setEndDateTime(request.getData().getEndDateTime()==null?null:request.getData().getEndDateTime());
			queryRequest.setUserId(request.getData().getUserId());
			queryRequest.setDeviceId(request.getData().getDeviceId() != null ? request.getData().getDeviceId(): null);
			
		
		} catch (Exception e) {
			logger.error("查询参数报错", e);
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}

		try {
			HealthyMonitorDateSleepResponse healthyMonitorDataSleepStruc = ejlHealthManageServiceImpl.getMonitorDataSleepById(queryRequest);
			getMonitorDataResponse.setHealthyMonitorData(mapper.writeValueAsString(healthyMonitorDataSleepStruc));
			fmlResponse.setData(getMonitorDataResponse);
			logger.info(mapper.writeValueAsString(healthyMonitorDataSleepStruc));
		} catch (Exception e) {
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			logger.error("getMonitorData", e);
		}
		return fmlResponse;
	}
	
	
	
	@RequestMapping("/getSleepStatus")
	@ResponseBody
	public Response<Integer> getSleepStatus(@RequestBody Request<GetMonitorDataRequest> request) throws BizException {
		Response<Integer> fmlResponse = new Response<Integer>(request);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		try{
			Integer status = ejlHealthManageServiceImpl.getSleepLockStatus(request.getData().getUserId(), request.getData().getDeviceId());
			fmlResponse.setData(status);
		}catch (Exception e) {
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			logger.error("getMonitorData", e);
		}
		return fmlResponse;
	}
}
