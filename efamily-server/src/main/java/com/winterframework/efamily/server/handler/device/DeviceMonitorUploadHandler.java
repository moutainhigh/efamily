package com.winterframework.efamily.server.handler.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.dto.device.DeviceMonitorStruc;
import com.winterframework.efamily.dto.device.DeviceStepStruc;
import com.winterframework.efamily.dto.device.HealthStepCountStruc;
import com.winterframework.efamily.dto.device.UserDeviceMonitorStruc;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 监听用户数据入库 
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 
 */
@Service("deviceMonitorUploadHandler")
public class DeviceMonitorUploadHandler extends AbstractHandler{
	private static final Logger log = LoggerFactory.getLogger(DeviceMonitorUploadHandler.class); 
	@PropertyConfig("device.monitor_upload")
	private String urlPath;
	
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Map<String, String> data = req.getData();
		DeviceMonitorStruc monitor = JsonUtils.fromJson(data.get("data"), DeviceMonitorStruc.class);
		UserDeviceMonitorStruc bizData = new UserDeviceMonitorStruc();
		log.info("28962 response :" +data.get("data"));
		FmlResponse res = new FmlResponse(req);
		if (null != monitor ) {
			bizData.setEndTime(DateUtils.currentDate());
			bizData.setDeviceUserId(monitor.getUserId());
			bizData.setDeviceId(ctx.getDeviceId());
			//设置status为结束
			bizData.setStatus(1);
			log.info("deviceMonitorUploadHandler:" + bizData);
			Response<StepResponse> bizRes = http(serverUrl,urlPath, ctx, bizData, StepResponse.class);
			res = getFmlResponse(res, bizRes);
		}
		return res;
	}
	
}