/**   
* @Title: DeviceBatteryQueryHandler.java 
* @Package com.winterframework.efamily.server.handler.device 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-6 下午1:22:34 
* @version V1.0   
*/
package com.winterframework.efamily.server.handler.device;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: DeviceBatteryQueryHandler 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-6 下午1:22:34 
*  
*/
@Deprecated
//调用参数接口
@Service("deviceBatteryMaxQueryHandler")
public class DeviceBatteryMaxQueryHandler extends AbstractHandler {
	@PropertyConfig("server.url.device")
	private String deviceServerUrl;
	@PropertyConfig("device.battery_query")
	private String urlPath;

	@Resource(name = "RedisClient")
	private RedisClient redisClient;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Map<String, String> data = req.getData();
		Long time = Long.valueOf(data.get("time"));
		String value = data.get("value");
		Long userId = ctx.getUserId();
		Long deviceId = ctx.getDeviceId();

		log.info("20195 response time:" + new Date(time) + "value:" + value);
		redisClient.set("maxBattery" + deviceId, value);
		FmlResponse res = new FmlResponse(req);
		return res;
	}

}
