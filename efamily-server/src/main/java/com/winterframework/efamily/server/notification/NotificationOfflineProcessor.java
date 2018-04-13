package com.winterframework.efamily.server.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.model.NotyRule;
import com.winterframework.efamily.base.model.NotyTask;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.modules.utils.SpringContextHolder;

public class  NotificationOfflineProcessor implements Runnable{ 
	private static final Logger log= LoggerFactory.getLogger(NotificationOfflineProcessor.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue"); 
	 
	private NotyTask notyTask;
	public NotificationOfflineProcessor(String dataStr){
		notyTask=JsonUtils.fromJson(dataStr, NotyTask.class);
	}
	@Override
	public void run() {
		log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+Thread.currentThread().getName());
	    process(); 
	}
	@SuppressWarnings("static-access")
	private void process(){ 
		NotyRule rule=notyTask.getRule();
		int expireTime=rule.getExpireTime();
		Long taskTime=notyTask.getTaskTime();
		if((DateUtils.getCurTime()-taskTime)/1000/60>expireTime){
			return;
		}
		boolean isConnected=ChannelManager.isConnected(notyTask.getTarget().getUserId(), notyTask.getTarget().getDeviceId());
		if(isConnected){
			addQueueHead(notyTask, false);
		}else{
			addQueue(notyTask, true);
		}
	} 
	/**
	 * 加入队首
	 * @param notyTask
	 * @param isOffline
	 */
	private void addQueueHead(NotyTask notyTask,boolean isOffline){
		//log.info("this is offline message is connection add :"+NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
		redisQueue.addHead(NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));  
	}
	private void addQueue(NotyTask notyTask,boolean isOffline){
		//log.info("this is offline message add :"+NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
		redisQueue.add(NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
	}
	
}
