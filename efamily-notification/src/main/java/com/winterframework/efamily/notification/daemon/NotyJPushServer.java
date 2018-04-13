package com.winterframework.efamily.notification.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.notification.enums.QueuePrefix;


@Service
public class NotyJPushServer {
	private static final Logger log = LoggerFactory.getLogger(NotyJPushServer.class); 
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(40);
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	
	@PostConstruct
	public void startup(){
		log.info("极光推送服务 启动......");
		final String queueName=QueuePrefix.NOTY_TP_PUSH; 
		
		new NotyThread(threadPool,queueName).start();
	}
	
	private class NotyThread extends Thread{
		ExecutorService threadPool;
		String queueName;
		public NotyThread(ExecutorService threadPool,String queueName){
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
						Thread.currentThread().sleep(10);
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
		String notyJPushStr=redisQueue.getUnique(queueName); 
		if(null!=notyJPushStr){
			threadPool.execute(new NotificationJPushProcessor(queueName,notyJPushStr));
		}
	}
}