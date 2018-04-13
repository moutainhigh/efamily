package com.winterframework.efamily.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.enums.QueuePrefix;
import com.winterframework.efamily.service.IEjlMessageService;

//@Service
public class MessageServer {
	private static final Logger log = LoggerFactory.getLogger(MessageServer.class); 
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(20);
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	@Resource(name = "ejlMessageServiceImpl")
	private IEjlMessageService ejlMessageService;
	
	
	//@PostConstruct
	public void startup(){
		log.info("聊天 发送  服务 启动......"); 
		
		new MessageThread(threadPool,QueuePrefix.QUEUE_MSG).start();
	}
	
	private class MessageThread extends Thread{
		ExecutorService threadPool;
		String queueName;
		public MessageThread(ExecutorService threadPool,String queueName){
			this.threadPool=threadPool;
			this.queueName=queueName;
		}
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			try {  
				while(true){
					long time1=DateUtils.getCurTime(); 
					try {
						Thread.currentThread().sleep(2);
					} catch(InterruptedException ex){
						log.error("Thread be interrupted.",ex);
					}
					//处理正常的消息队列
					try{
						execute(threadPool,queueName);
					}catch(Exception e){
						log.error("消息处理 execute failed.",e);
					}
					long time2=DateUtils.getCurTime();
					log.debug("notyServer cost time:"+(time2-time1));
				}
			} catch (Exception e) {
				log.error("消息 服务 异常:"+queueName,e);
			} 
		} 
	}
	private void execute(ExecutorService threadPool,String queueName) {
		long time1=DateUtils.getCurTime(); 
		String msgIdStr=redisQueue.get(queueName);
		final Long msgId=null!=msgIdStr?Long.valueOf(msgIdStr):null;
		long time2=DateUtils.getCurTime();
		log.debug("notyServer redis cost time:"+(time2-time1));
		if(null!=msgId){
			threadPool.execute(new Runnable(){
				public void run() {
					try{
						process(msgId);
					}catch(Exception e){
						
					}
				};
			});
		}
		long time3=DateUtils.getCurTime();
		log.debug("notyServer process cost time:"+(time3-time2));
	}
	protected void process(Long msgId) throws BizException{
		/**
		 * 1.get user list
		 * 2.save message_mark
		 * 3.noty user
		 * 
		 */
		EjlMessage msg=ejlMessageService.get(msgId);
		if(null!=msg){
			Long sendUserId=msg.getSendUserId();
			Long chatType=msg.getChatType();
			Long receiveUserId=msg.getReceiveUserId();
		}
		
	}
	private void addQueueUser(String queueNameTarget){
		//log.info("this is offline message add :"+NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
		redisQueue.add(NotyQueue.QUEUE_USER,queueNameTarget);
	}
}