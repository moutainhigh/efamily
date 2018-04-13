package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("deviceOperationResetStrategy")
@Transactional(rollbackFor = BizException.class)
public class DeviceOperationResetStrategy extends AbstractDeviceOperationStrategy {
	@PropertyConfig(value="task.url")
	private String serverUrl;
	@PropertyConfig(value="task.sendUserWeather")
	private String sendUserWeather;
	@PropertyConfig(value="task.sendUserLunar")
	private String sendUserLunar;
	@Resource(name="httpUtil")
	private HttpUtil httpUtil;
	@Override
	protected void doBiz(Context ctx,Integer code, Long time, Integer status)
			throws BizException {
		log.error("reset occured."+" deviceId="+ctx.getDeviceId());
		try {  
			Map<String,Long> map = new HashMap<String,Long>();
			map.put("userId", ctx.getUserId());
			map.put("deviceId", ctx.getDeviceId());
			httpUtil.http(serverUrl,sendUserWeather, map);
			httpUtil.http(serverUrl, sendUserLunar, map);
		} catch (Exception e) {
			log.error("reset error userId"+ctx.getUserId(), e);
		} 
	}

}
