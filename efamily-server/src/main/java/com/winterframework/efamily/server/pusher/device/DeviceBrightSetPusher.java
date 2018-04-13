package com.winterframework.efamily.server.pusher.device;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
 
/**
 * 设备亮度设置推送类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("deviceBrightSetPusher")
public class DeviceBrightSetPusher extends AbstractPusher{
	@Override
	protected Map<String, String> getData(Context ctx, FmlRequest req) throws ServerException{
		Map<String,String> data=new HashMap<String,String>();
		data.put("volume",req.getData().get("parameterContext"));
		return data;
	}
}