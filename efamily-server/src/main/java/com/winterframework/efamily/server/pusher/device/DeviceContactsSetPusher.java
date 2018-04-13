package com.winterframework.efamily.server.pusher.device;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.exception.ServerException;
 
/**
 * 设备通讯录设置推送类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("deviceContactsSetPusher")
public class DeviceContactsSetPusher extends AbstractPusher{
	@Override
	protected Map<String, String> getData(Context ctx,
			Map<String, String> data) throws ServerException {
		String dataStr=data.get("data");
		dataStr=dataStr.replace("name", "nickName");
		data.put("data", dataStr);
		return data;
	}
}