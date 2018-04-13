package com.winterframework.efamily.server.pusher.device;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DevicePowerAutoRequest;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.exception.ServerException;
 
/**
 * 设备自动开关机设置推送类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("devicePowerAutoSetPusher")
public class DevicePowerAutoSetPusher extends AbstractPusher{
	
	@Override
	protected Map<String, String> getData(Context ctx,
			Map<String, String> data) throws ServerException {
		String ons=data.get("ons");
		String offs=data.get("offs");
		DevicePowerAutoRequest bizReq=new DevicePowerAutoRequest();
		bizReq.setOns(JsonUtils.fromJson2List(ons, String.class));
		bizReq.setOffs(JsonUtils.fromJson2List(offs, String.class));
		data.clear();
		data.put("data", JsonUtils.toJson(bizReq));
		return data;
	}
}