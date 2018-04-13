package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.UpdateUserLoginRecordRequest;
import com.winterframework.efamily.dto.UpdateUserLoginRecordResponse;
import com.winterframework.efamily.dto.device.DeviceBindConfirmRequest;
import com.winterframework.efamily.dto.device.DeviceBindConfirmResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 设备绑定确认
 * 
 * @ClassName
 * @Description
 * @author ibm 2015年3月17日
 */
@Service("deviceBindPushHandler")
public class DeviceBindPushHandler extends AbstractHandler {
	@PropertyConfig("server.url.device")
	private String serverUrl;
	@PropertyConfig("server.url.app")
	private String serverUrlApp;
	@PropertyConfig("device.bind")
	private String urlPath;
	@PropertyConfig("app.server.update_user_login_record")
	private String updateUserLoginRecord;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req)
			throws ServerException {
		DeviceBindConfirmRequest bizReq = new DeviceBindConfirmRequest();
		bizReq.setStatus(req.getStatus());
		FmlResponse res = new FmlResponse(req);
		Response<DeviceBindConfirmResponse> bizRes = http(serverUrl,urlPath, ctx, bizReq,
				DeviceBindConfirmResponse.class);
		res = getFmlResponse(res, bizRes);
		if(null!=bizRes && YesNo.NO.getValue()==res.getStatus()){ 
			UpdateUserLoginRecordRequest  loginReq =  new UpdateUserLoginRecordRequest();
			loginReq.setStatus(1);// status 1:登录 
			loginReq.setToken(req.getToken());
			loginReq.setRemark("ip="+req.getIp()+";clientType="+req.getClinetType()+";");
			Response<UpdateUserLoginRecordResponse> biz=http(serverUrlApp,updateUserLoginRecord,ctx,bizReq,UpdateUserLoginRecordResponse.class);
			if(biz!=null){
				log.debug("记录用户登录状态成功： userId = "+ctx+" ; token = "+req.getToken()+" ; ");
			}
		}
		return res;
	}
}