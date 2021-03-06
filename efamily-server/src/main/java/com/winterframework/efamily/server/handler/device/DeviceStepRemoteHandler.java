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
import com.winterframework.efamily.dto.device.DeviceStepStruc;
import com.winterframework.efamily.dto.device.HealthStepCountStruc;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 设备心率查询处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 * 设备响应的计步数据入库 
 */
@Service("deviceStepRemoteHandler")
public class DeviceStepRemoteHandler extends AbstractHandler {
	private static final Logger log = LoggerFactory.getLogger(DeviceStepQueryHandler.class);
	@PropertyConfig("device.step_upload")
	private String urlPath;
	@PropertyConfig("server.url.device")
	private String serverUrl;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Map<String, String> data = req.getData();
		Long userId = ctx.getUserId();
		Long time = Long.valueOf(data.get("time"));
		List<DeviceStepStruc> steps = JsonUtils.fromJson2List(data.get("data"), DeviceStepStruc.class);
		List<HealthStepCountStruc> bizData = new ArrayList<HealthStepCountStruc>();
		log.info("20824 response time:" + DateUtils.getDate(time)+":"+data.get("data"));
		FmlResponse res = new FmlResponse(req);
		if (null != steps) {
			for (DeviceStepStruc step : steps) {
				HealthStepCountStruc dbCount = new HealthStepCountStruc();
				dbCount.setBegintime(DateUtils.convertLong2Date(step.getFromTime()));
				dbCount.setEndtime(DateUtils.convertLong2Date(step.getToTime()));
				dbCount.setUserId(userId);
				dbCount.setSteps(step.getCount());
				bizData.add(dbCount);
			}
			Response<StepResponse> bizRes = http(serverUrl,urlPath, ctx, bizData, StepResponse.class);
			res = getFmlResponse(res, bizRes);
		}
		return res;
	}

}