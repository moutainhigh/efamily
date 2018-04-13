package com.winterframework.efamily.server.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.modules.utils.SpringContextHolder;

public class  NotificationBindOfflineProcessor implements Runnable{ 
	private static final Logger log= LoggerFactory.getLogger(NotificationBindOfflineProcessor.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue"); 
	 
	private String notyBindStr; 
	public NotificationBindOfflineProcessor(String notyBindStr){
		this.notyBindStr=notyBindStr; 
	}
	@Override
	public void run() {
		log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+Thread.currentThread().getName());
	    process(); 
	} 
	private void process(){ 
		NotificationBind notyBind=JsonUtils.fromJson(notyBindStr, NotificationBind.class);
		if(ChannelManager.isConnected(notyBind.getImei())){
			addQueueWithTry(NotyQueue.QUEUE_BIND,notyBindStr);
		}else{
			addQueueWithTry(NotyQueue.QUEUE_BIND_OFFLINE,notyBindStr);
		}
	}
	private void addQueueWithTry(String queueName,String value) {
		try{
			addQueue(queueName,value);
		}catch(Exception e){
			log.error("add queue offline error.",e);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				log.error("thread be interrupted.",e);
				e1.printStackTrace();
			}
			try{
				addQueue(queueName,value);
			}catch(Exception e2){
				log.error("add queue offline error.",e2);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					log.error("thread be interrupted.",e);
				}
				addQueue(queueName,value);
			}
		}
	} 
	private void addQueue(String queueName,String value){
		//log.info("this is offline message add :"+NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
		redisQueue.add(queueName,value);
	}
	
	
}
