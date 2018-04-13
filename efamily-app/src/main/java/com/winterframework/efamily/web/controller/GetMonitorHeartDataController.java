package com.winterframework.efamily.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
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
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserNotice;
import com.winterframework.efamily.service.IEjlComUserNoticeService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlHealthManageService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("getMonitorHeartDataController")
@RequestMapping("/server")
public class GetMonitorHeartDataController {

	@Resource(name = "ejlHealthManageServiceImpl")
	private IEjlHealthManageService ejlHealthManageServiceImpl;
	
	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService 	ejlUserDeviceServiceImpl;
	
	@Resource(name = "ejlComUserNoticeServiceImpl")
	private IEjlComUserNoticeService ejlComUserNoticeServiceImpl;
	@Resource(name = "ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceService;
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserService;
	
	private static final Logger logger = LoggerFactory.getLogger(GetMonitorHeartDataController.class);
	@RequestMapping("/getMonitorHeartData")
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
			queryRequest.setUserId(request.getData().getUserId());
			queryRequest.setDeviceId(request.getData().getDeviceId() != null ? request.getData().getDeviceId(): null);
			queryRequest.setStartDateTime(request.getData().getStartDateTime());
			queryRequest.setEndDateTime(request.getData().getEndDateTime());
			
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
			message.setCommand(20725);
			Map<String,String> data=new HashMap<String,String>();
			data.put("command", "20725");
			message.setData(data);
			notification.setMessage(message);
			//httpClientImpl.invokeHttp(serverUrl+serverUrlActionPush,notification, Map.class);
		} catch (Exception e) {
			logger.error("查询参数报错", e);
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}

		try {
			HealthyMonitorDataStruc healthyMonitorDataStruc = ejlHealthManageServiceImpl.getMonitorHeartDataById(queryRequest);
			getMonitorDataResponse.setHealthyMonitorData(mapper.writeValueAsString(healthyMonitorDataStruc.getUnitDatas()));
			getMonitorDataResponse.setBottomValue(healthyMonitorDataStruc.getBottomValue());
			getMonitorDataResponse.setTopValue(healthyMonitorDataStruc.getTopValue());
			getMonitorDataResponse.setMiddleValue(healthyMonitorDataStruc.getMiddleValue());
			
			
			EjlUserNotice entity = new EjlUserNotice();
			entity.setUserId(request.getUserId());
			entity.setDeviceUserId(request.getData().getUserId());
			Context ctx = new Context();
			ctx.set("userId", request.getUserId());
			EjlUserNotice ejlUserNotice = ejlComUserNoticeServiceImpl.selectOneObjByAttribute(ctx, entity);
			getMonitorDataResponse.setRateRangeGt(ejlUserNotice==null||ejlUserNotice.getRateRangeGt()==null?0:Long.valueOf(ejlUserNotice.getRateRangeGt()));
			getMonitorDataResponse.setRateRangeLt(ejlUserNotice==null||ejlUserNotice.getRateRangeLt()==null?0:Long.valueOf(ejlUserNotice.getRateRangeLt()));
			
			EjlDevice  device=ejlDeviceService.get(queryRequest.getDeviceId());
			if(device!=null && device.getOperType()!=null && device.getOperType().intValue()==EjlDevice.OperType.HEART.getValue()
					&& device.getOperStatus()!=null && device.getOperStatus().intValue()!=EjlDevice.OperStatus.INIT.getValue() 
					&& device.getOperStatus().intValue()!=EjlDevice.OperStatus.FINISH.getValue()){
				if(device.getOperTime() != null && DateUtils.addMinutes(device.getOperTime(), 5).after(new Date())){
					EjlUser user=ejlUserService.get(device.getOperUserId());
					if(user!=null){
						getMonitorDataResponse.setOperUserId(user.getId());
						getMonitorDataResponse.setOperUserName(user.getNickName());
					}
				}else{
					device.setOperStatus(EjlDevice.OperStatus.INIT.getValue());
					ejlDeviceService.save(ctx, device);
				}
			}
			
			fmlResponse.setData(getMonitorDataResponse);
			logger.info(mapper.writeValueAsString(healthyMonitorDataStruc));
		} catch (Exception e) {
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			logger.error("getMonitorHeartData", e);
		}
		return fmlResponse;
	}
}
