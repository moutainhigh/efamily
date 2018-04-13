package com.winterframework.efamily.server.pusher.device;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.enums.ParameterConvertor;
import com.winterframework.efamily.server.exception.ServerException;
 
/**
 * 设备参数设置推送类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("deviceParamSetPusher")
public class DeviceParamSetPusher extends AbstractPusher{	 
	@Override
	protected Map<String, String> getData(Context ctx,
			Map<String, String> data) throws ServerException { 
		Integer code=ParameterConvertor.get(Integer.valueOf(data.get("paramCode")));
		String value=data.get("paramValue");
		data.clear();
		data.put("code", code+"");
		data.put("value", value);
		return data;
	}
}