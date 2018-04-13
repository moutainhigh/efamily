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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.GetMonitorDataRequest;
import com.winterframework.efamily.dto.GetMonitorDataResponse;
import com.winterframework.efamily.dto.HealthyMonitorDataStruc;
import com.winterframework.efamily.dto.QueryMonitorDataRequest;
import com.winterframework.efamily.service.IEjlHealthManageService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("getMonitorDataController")
@RequestMapping("/server")
public class GetMonitorDataController {

	@Resource(name = "ejlHealthManageServiceImpl")
	private IEjlHealthManageService ejlHealthManageServiceImpl;
	
	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService 	ejlUserDeviceServiceImpl;
	
	@Resource(name="httpClientImpl")
	protected IHttpClient httpClientImpl;
	
	@PropertyConfig(value="server.url")
	private String serverUrl;

	private static final Logger logger = LoggerFactory.getLogger(GetMonitorDataController.class);

	@RequestMapping("/getMonitorData")
	@ResponseBody
	protected Response<GetMonitorDataResponse> doHandle(@RequestBody Request<GetMonitorDataRequest> request) throws BizException {
		Response<GetMonitorDataResponse> fmlResponse = new Response<GetMonitorDataResponse>(request);
		GetMonitorDataResponse getMonitorDataResponse = new GetMonitorDataResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ObjectMapper mapper = new ObjectMapper();
		QueryMonitorDataRequest queryRequest = new QueryMonitorDataRequest();
		try {
			queryRequest.setCurrentPage(request.getData().getCurrentPage() != null ? request.getData().getCurrentPage() : null);
			queryRequest.setPerPageSize(request.getData().getPerPageSize() != null ? request.getData().getPerPageSize() : null);
			queryRequest.setDateType(request.getData().getDateType()==null?null:request.getData().getDateType()); 
			queryRequest.setMonitorDataType(request.getData().getMonitorDataType());
			queryRequest.setStartDateTime(request.getData().getStartDateTime()==null?null:request.getData().getStartDateTime());
			queryRequest.setEndDateTime(request.getData().getEndDateTime()==null?null:request.getData().getEndDateTime());
			queryRequest.setUserId(request.getData().getUserId());
			queryRequest.setDeviceId(request.getData().getDeviceId() != null ? request.getData().getDeviceId(): null);
			
			Notification notification = new Notification();
			NotyTarget notyTarget = new NotyTarget();
			notyTarget.setUserId(request.getData().getUserId());
			Long deviceId = request.getData().getDeviceId();
			if(request.getData().getDeviceId() == null){
			deviceId = ejlUserDeviceServiceImpl.getUserUseingDeviceId(request.getData().getUserId());}
			notyTarget.setDeviceId(deviceId);
			notification.setTarget(notyTarget);
			NotyMessage message = new NotyMessage();
			message.setType(message.getType().OPER);
			message.setCommand(20825);
			Map<String,String> data=new HashMap<String,String>();
			data.put("command", "20825");
			message.setData(data);
			notification.setMessage(message);
			//httpClientImpl.invokeHttp(serverUrl+serverUrlActionPush,notification, Map.class);
			
		
		} catch (Exception e) {
			logger.error("查询参数报错", e);
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}

		try {
			HealthyMonitorDataStruc healthyMonitorDataStruc = ejlHealthManageServiceImpl.getMonitorDataById(queryRequest);
			getMonitorDataResponse.setHealthyMonitorData(mapper.writeValueAsString(healthyMonitorDataStruc.getUnitDatas()));
			getMonitorDataResponse.setTotalTime(healthyMonitorDataStruc.getTotalTime());
			fmlResponse.setData(getMonitorDataResponse);
			logger.info(mapper.writeValueAsString(healthyMonitorDataStruc));
		} catch (Exception e) {
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			logger.error("getMonitorData", e);
		}
		return fmlResponse;
	}
}
