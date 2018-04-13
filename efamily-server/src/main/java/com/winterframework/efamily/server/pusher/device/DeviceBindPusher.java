package com.winterframework.efamily.server.pusher.device;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceBindRequest;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.exception.ServerException;
 
/**
 * 设备绑定推送类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("deviceBindPusher")
public class DeviceBindPusher extends AbstractPusher{
	@Override
	protected Map<String, String> getData(Context ctx,
			Map<String, String> data) throws ServerException {
		DeviceBindRequest bizReq=new DeviceBindRequest();
		bizReq.setUserId(Long.valueOf(data.get("userId")));
		bizReq.setNickName(data.get("nickName"));
		bizReq.setPhoneNumber(data.get("phoneNumber"));
		bizReq.setFamilyName(data.get("familyName"));
		data.clear();
		data.put("data", JsonUtils.toJson(bizReq));
		return data;
	}
}