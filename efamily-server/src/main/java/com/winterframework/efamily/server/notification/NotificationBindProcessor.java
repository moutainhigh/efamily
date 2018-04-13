package com.winterframework.efamily.server.notification;

import io.netty.channel.Channel;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.CharsetFactory;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.IPushService;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.utils.SpringContextHolder;

public class NotificationBindProcessor implements Runnable{ 
	private static final Logger log= LoggerFactory.getLogger(NotificationBindProcessor.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue");
	private final IPushService pushService=SpringContextHolder.getBean("pushServiceImpl");
	 
	private String queueName; 
	private String notyBindStr; 
	public NotificationBindProcessor(String queueName,String notyBindStr){ 
		this.queueName=queueName;
		this.notyBindStr=notyBindStr;
	}
	@Override
	public void run() {
		//log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+Thread.currentThread().getName());
	    process(); 
	}
	
	private void process(){
		try{
			NotificationBind notyBind=JsonUtils.fromJson(notyBindStr, NotificationBind.class);
			boolean isFinished=doProcess(notyBind);
			boolean hasOffline=false;
			if(isFinished){
				redisQueue.del(queueName, notyBindStr);
			}else{
				redisQueue.add(queueName, notyBindStr);
				hasOffline=true;
			}
			if(hasOffline){
				redisQueue.add(NotyQueue.QUEUE_BIND_OFFLINE, notyBindStr);
			}
		}catch(Exception e){
			log.error("process notification error. queueName="+queueName,e);
		} 
	}
	
	/**
	 * @param notyTask
	 * @param isOfflineQueue
	 * @return 是否用户已经断线（断线则下次再处理）
	 */
	private boolean doProcess(NotificationBind notyBind) {
		try {
			//正常连接
			String imei=notyBind.getImei();
			Channel channel=ChannelManager.get(imei);
			if(ChannelManager.isConnected(channel)){
				Long userId=notyBind.getUserId();
				Long deviceId=notyBind.getDeviceId();
				ChannelManager.save(userId,deviceId,channel);
				Context ctx=new Context(userId,deviceId);
				ctx.set("nickName",notyBind.getNickname());
				ctx.set("phoneNumber",notyBind.getPhoneNumber());
				ctx.set("familyName",notyBind.getFamilyName());
							
				FmlResponse response=new FmlResponse();
				response.setEncode((byte) CharsetFactory.CharsetEnum.UTF8.getCode());
				response.setEncrypt((byte) YesNo.NO.getValue());
				response.setVersion((byte)1);
				response.setClinetType((byte)-1);
				response.setExtend((byte)0);
				response.setSessionId(DateUtils.getCurTime());
				response.setCommand(Command.DEVICE_BIND_PUSH.getCode()); //消息推送
				response.setStatus(null!=deviceId?StatusCode.REQUEST.getValue():StatusCode.OK.getValue());  //默认0，ok
				//res.setStatus(StatusCode.REQUEST.getValue()); 
				response.setToken(TokenManager.save(ctx)); 
				response.setData(new HashMap<String,String>());
					
				pushService.push(userId,deviceId,response);
			}
		} catch (ServerException e) {
			log.error("send mq error and rollback->notyBind:"+JsonUtils.toJson(notyBind),e);
			return false;
		}
		return true;
	}
}
