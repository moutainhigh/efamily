package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.device.DevicePowerRequest;
import com.winterframework.efamily.dto.device.DevicePowerResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备开关机处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("devicePowerOnffHandler")
public class DevicePowerOnffHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.power_onff")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {	
		/**
		 * 更新设备开关机状态
		 * 通知家庭成员
		 */
		DevicePowerRequest bizData=new DevicePowerRequest();
		bizData.setStatus(Integer.valueOf(req.getValue("status")));
		FmlResponse res=new FmlResponse(req);
		Response<DevicePowerResponse> bizRes=http(serverUrl,urlPath,ctx,bizData,DevicePowerResponse.class);	
		res=getFmlResponse(res,bizRes);
		//?????notify(NoriceRange.ALL,NoticeType.POWER,familyId);
		return res;
	}
	
}