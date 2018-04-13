package com.winterframework.efamily.server.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.server.notification.NotificationBindOfflineProcessor;

//暂时禁用20009 @Service
public class NotyBindOfflineServer {
	private static final Logger log = LoggerFactory.getLogger(NotyBindOfflineServer.class);  
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(2);
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@PostConstruct
	public void startup(){
		log.info("设备 绑定 离线 服务 启动......"); 
		new NotyOfflineThread(threadPool,NotyQueue.QUEUE_BIND_OFFLINE).start();
	}
	
	private class NotyOfflineThread extends Thread{
		ExecutorService threadPool;
		String queueName;
		public NotyOfflineThread(ExecutorService threadPool,String queueName){
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
						Thread.currentThread().sleep(20);
					} catch(InterruptedException ex){
						log.error("Thread be interrupted.",ex);
					}
					//处理正常的消息队列
					try{ 
						execute(threadPool,queueName);
					}catch(Exception e){
						log.error("消息 离线 处理 execute failed.",e);
					}
					long time2=DateUtils.getCurTime();
					log.debug("notyServer cost time:"+(time2-time1));
				}
			} catch (Exception e) {
				log.error("消息 离线 服务 异常:"+queueName,e);
			} 
		} 
	}
	private void execute(ExecutorService threadPool,String queueName) {
		String notyBindStr=redisQueue.getUnique(queueName);
		//List<String> values=redisQueue.get(queueName,queueName + "_offline",queueName+"_offline_1");  
		if(null!=notyBindStr){
			threadPool.execute(new NotificationBindOfflineProcessor(notyBindStr));
		}
	}
}