package com.winterframework.efamily.server.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.server.notification.NotificationBindProcessor;

//暂时禁用20009 @Service
public class NotyBindServer {
	private static final Logger log = LoggerFactory.getLogger(NotyBindServer.class); 
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	
	@PostConstruct
	public void startup(){
		log.info("消息（绑定） 服务 启动......");
		final String queueName=NotyQueue.QUEUE_BIND; 
		
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
		String notyBindStr=redisQueue.getUnique(queueName); 
		if(null!=notyBindStr){
			threadPool.execute(new NotificationBindProcessor(queueName,notyBindStr));
		}
	}
}