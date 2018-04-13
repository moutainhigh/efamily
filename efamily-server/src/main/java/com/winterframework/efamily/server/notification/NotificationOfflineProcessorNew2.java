package com.winterframework.efamily.server.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.model.NotyRule;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.NotyTask;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.modules.utils.SpringContextHolder;

public class  NotificationOfflineProcessorNew2 implements Runnable{ 
	private static final Logger log= LoggerFactory.getLogger(NotificationOfflineProcessorNew2.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue"); 
	 
	private String queueNameTarget; 
	public NotificationOfflineProcessorNew2(String queueNameTarget){
		this.queueNameTarget=queueNameTarget; 
	}
	@Override
	public void run() {
		log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+Thread.currentThread().getName());
	    process(); 
	} 
	private void process(){ 
		NotyTarget target=NotificationProcessorNew2.getTarget(queueNameTarget);
		boolean isConnected=ChannelManager.isConnected(target.getUserId(), target.getDeviceId());
		if(isConnected){
			addQueueWithTry(NotyQueue.QUEUE_USER,queueNameTarget);
		}else{
			addQueueWithTry(NotyQueue.QUEUE_USER_OFFLINE,queueNameTarget);
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
				addQueue(queueNameTarget);
			}catch(Exception e2){
				log.error("add queue offline error.",e2);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					log.error("thread be interrupted.",e);
				}
				addQueue(queueNameTarget);
			}
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
	private void addQueue(String queueNameTarget){
		//log.info("this is offline message add :"+NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
		redisQueue.add(NotyQueue.QUEUE_USER_OFFLINE,queueNameTarget);
	}
	private void addQueueUser(String queueNameTarget){
		//log.info("this is offline message add :"+NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
		redisQueue.add(NotyQueue.QUEUE_USER,queueNameTarget);
	}
	private void addQueue(String queueName,String value){
		//log.info("this is offline message add :"+NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
		redisQueue.add(queueName,value);
	}
	
	
}
