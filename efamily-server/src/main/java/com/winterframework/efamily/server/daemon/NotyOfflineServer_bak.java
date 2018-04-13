package com.winterframework.efamily.server.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.server.notification.NotificationOfflineProcessor;

public class NotyOfflineServer_bak {
	private static final Logger log = LoggerFactory.getLogger(NotyOfflineServer.class); 
	/*
	 *  OPER:ONE
		SETT:TWO
		ALARM:THREE
		REMIND:FOUR
		MSG:FIVE
		NOTICE:SIX
	 */
	
	private static final int multiply=5;
	private static final ExecutorService threadPool0 = Executors.newFixedThreadPool(30*multiply);
	private static final String queueOnline="queue_online";
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@PostConstruct
	public void startup(){
		log.info("消息 离线 服务 启动......"); 
		new NotyOfflineThread(threadPool0,queueOnline).start();
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
				/*while(true){
					try {
						Thread.currentThread().sleep(2);
					} catch(InterruptedException ex){
						log.error("Thread be interrupted.",ex);
					}
					//处理正常的消息队列
					execute(threadPool,queueName); 
				}*/
				execute(threadPool,queueName);
			} catch (Exception e) {
				log.error("消息 服务 异常:"+queueName,e);
			} 
		} 
	}
	private void execute(ExecutorService threadPool,String queueName) {
		//String value=redisQueue.get(queueName);
		//List<String> values=redisQueue.get(queueName,queueName + "_offline",queueName+"_offline_1");  
		/*if(null==value){
			log.debug("notification in the queue all has been pushed.");
			return;
		}else{
			log.info("this is offline queue value:"+value);
		}
		for(String s:values)
			log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->"+s);
		if(null==values.get(1)){
			log.debug("notification in the queue is empty.");
			return;
		}
		String onlineValue=value;
		String[] v=onlineValue.split(Separator.verticalSep); 
		Long userId=new Long(v[0]);
		Long deviceId=(v.length==2 && (null!=v[1] && !"".equals(v[1])))?new Long(v[1]):null;*/
		
		/*threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.ZERO.getValue(),null, deviceId));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.ONE.getValue(),userId, deviceId));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.TWO.getValue(),userId, deviceId));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.THREE.getValue(),userId, deviceId));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.FOUR.getValue(),userId, deviceId));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.FIVE.getValue(),userId,deviceId));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.SIX.getValue(),userId,deviceId));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.SEVEN.getValue(),userId,deviceId));*/
		
		
		
		/*threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.ZERO.getValue(),null, null));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.ONE.getValue(),null, null));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.TWO.getValue(),null, null));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.THREE.getValue(),null, null));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.FOUR.getValue(),null, null));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.FIVE.getValue(),null,null));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.SIX.getValue(),null,null));
		threadPool.execute(new NotificationOfflineProcessor(NotyQueue.Index.SEVEN.getValue(),null,null));*/
	}
}