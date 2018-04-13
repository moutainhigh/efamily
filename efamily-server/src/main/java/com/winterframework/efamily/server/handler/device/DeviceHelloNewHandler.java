package com.winterframework.efamily.server.handler.device;

import io.netty.channel.Channel;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.device.DeviceHelloNewRequest;
import com.winterframework.efamily.dto.device.DeviceHelloNewResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 设备向服务器打招呼处理类
 * 
 * @ClassName
 * @Description
 * @author ibm 2015年3月17日
 */
@Service("deviceHelloNewHandler")
public class DeviceHelloNewHandler extends AbstractHandler {
	@PropertyConfig("server.url.device")
	private String serverUrl;
	@PropertyConfig("device.hello.new")
	private String urlPath;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException{ 
		DeviceHelloNewRequest bizReq=new DeviceHelloNewRequest();
		String imei=req.getValue("imei");
		bizReq.setImei(imei);
		FmlResponse res=new FmlResponse(req);
		Response<DeviceHelloNewResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceHelloNewResponse.class);	  
		res=getFmlResponse(res,bizRes);
		if(res.getStatus()==YesNo.NO.getValue()){ 	//请求成功
			if(null!=bizRes.getData()){
				/**
				 * 有绑表请求 则推送token
				 * 无绑表请求 则保存连接
				 */
				if(bizRes.getData().isHasBindReq()){
					Long userId=bizRes.getData().getUserId();
					Long deviceId=bizRes.getData().getDeviceId();
					ChannelManager.save(userId,deviceId,(Channel)ctx.get("channel"));
					
					ctx.set("userId",bizRes.getData().getUserId());
					ctx.set("deviceId",bizRes.getData().getDeviceId());
					ctx.set("nickName",bizRes.getData().getNickname());
					ctx.set("phoneNumber",bizRes.getData().getPhoneNumber());
					ctx.set("familyName",bizRes.getData().getFamilyName());
					res.setToken(getToken(ctx));
				}else{
					ChannelManager.save(imei,(Channel)ctx.get("channel"));
				}
			}
		}
		return res;
	}
	private String getToken(Context ctx) {
		return TokenManager.save(ctx);
	}

}