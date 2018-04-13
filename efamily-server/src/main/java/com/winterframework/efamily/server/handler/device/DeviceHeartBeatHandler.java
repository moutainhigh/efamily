package com.winterframework.efamily.server.handler.device;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备心跳包处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("deviceHeartBeatHandler")
public class DeviceHeartBeatHandler extends AbstractHandler{
	@PropertyConfig("server.url.device")
	private String serverUrl; 
	@PropertyConfig("device.hello")
	private String urlPath;
	@Resource(name = "RedisClient")
	private RedisClient redisClient;  
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException{
		Long userId=ctx.getUserId();
		Long deviceId=ctx.getDeviceId();
		//是否存在该用户设备的未处理数据
		boolean hasData=false;//Boolean.valueOf(redisClient.get(userId+deviceId+""));
		FmlResponse res=new FmlResponse(req);
		if(hasData){
			res.setStatus(StatusCode.CONTINUE.getValue());
			//通知推送体系发送消息
		}else{
			res.setStatus(StatusCode.OK.getValue());
		}
		return res;
	}	
}