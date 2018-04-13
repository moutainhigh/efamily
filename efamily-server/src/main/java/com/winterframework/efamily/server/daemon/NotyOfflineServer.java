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
import com.winterframework.efamily.server.notification.NotificationOfflineProcessor;

@Service
public class NotyOfflineServer {
	private static final Logger log = LoggerFactory.getLogger(NotyOfflineServer.class); 
	/*
	 *  OPER:ONE
		SETT:TWO
		ALARM:THREE
		REMIND:FOUR
		MSG:FIVE
		NOTICE:SIX
	 */
	
	private static final int multiply=1;
	private static final ExecutorService threadPool0 = Executors.newFixedThreadPool(30*multiply);
	@Resource(name="redisQueue")
	private RedisQueue redisQueue; 
	
//	@PostConstruct 
	public void startup(){
		log.info("消息 离线 服务 启动......"); 
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.ZERO.getValue(), true, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.ONE.getValue(), true, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.TWO.getValue(), true, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.THREE.getValue(), true, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.FOUR.getValue(), true, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.FIVE.getValue(), true, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.SIX.getValue(), true, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.SEVEN.getValue(), true, true).getName()).start();
		
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.ZERO.getValue(), false, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.ONE.getValue(), false, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.TWO.getValue(), false, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.THREE.getValue(), false, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.FOUR.getValue(), false, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.FIVE.getValue(), false, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.SIX.getValue(), false, true).getName()).start();
		new NotyOfflineThread(threadPool0,NotyQueue.getQueue(NotyQueue.Index.SEVEN.getValue(), false, true).getName()).start();
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
						Thread.currentThread().sleep(2);
					} catch(InterruptedException ex){
						log.error("Thread be interrupted.",ex);
					}
					//处理正常的消息队列
					execute(threadPool,queueName);
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
		String value=redisQueue.get(queueName);
		long time2=DateUtils.getCurTime();
		log.debug("notyServer redis cost time:"+(time2-time1));
		//List<String> values=redisQueue.get(queueName,queueName + "_offline",queueName+"_offline_1");  
		if(null==value){
			//log.info("notification in the queue all has been pushed.");
			return;
		}else{
			//log.info("notification off line value is"+value);
		}
		
		/*for(String s:values)
			log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->"+s);*/
		/*if(null==values.get(1)){
			//log.debug("notification in the queue is empty.");
			return;
		}*/
		threadPool.execute(new NotificationOfflineProcessor(value));
		long time3=DateUtils.getCurTime();
		log.debug("notyServer process cost time:"+(time3-time2));
	}
}