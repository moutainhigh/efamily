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
import com.winterframework.efamily.server.notification.NotificationProcessor;

@Service
public class NotyServer {
	private static final Logger log = LoggerFactory.getLogger(NotyServer.class); 
	private static final int multiply=1;
	/*
	 *  OPER:ONE
		SETT:TWO
		ALARM:THREE
		REMIND:FOUR
		MSG:FIVE
		NOTICE:SIX
	 */
	
	private static final ExecutorService threadPool0 = Executors.newFixedThreadPool(8*multiply);
	private static final ExecutorService threadPool1 = Executors.newFixedThreadPool(16*multiply);
	//private static final ExecutorService threadPool2 = Executors.newFixedThreadPool(30*multiply);
	private static final ExecutorService threadPool3 = Executors.newFixedThreadPool(20*multiply);
	private static final ExecutorService threadPool4 = Executors.newFixedThreadPool(3*multiply);
	private static final ExecutorService threadPool5 = Executors.newFixedThreadPool(80*multiply);
	private static final ExecutorService threadPool6 = Executors.newFixedThreadPool(40*multiply); 
	
	private static final ExecutorService threadPoolDevice0 = Executors.newFixedThreadPool(8*multiply);
	private static final ExecutorService threadPoolDevice1 = Executors.newFixedThreadPool(20*multiply);
	private static final ExecutorService threadPoolDevice2 = Executors.newFixedThreadPool(10*multiply);
	//private static final ExecutorService threadPoolDevice3 = Executors.newFixedThreadPool(10*multiply);
	private static final ExecutorService threadPoolDevice4 = Executors.newFixedThreadPool(2*multiply);
	private static final ExecutorService threadPoolDevice5 = Executors.newFixedThreadPool(30*multiply);
	private static final ExecutorService threadPoolDevice6 = Executors.newFixedThreadPool(20*multiply);
	
	@Resource(name="redisQueue")
	private RedisQueue redisQueue; 
	//@PostConstruct 
	public void startup(){
		log.info("消息 服务 启动......");
		final String queueName0=NotyQueue.getQueue(NotyQueue.Index.ZERO.getValue(),false,false).getName();
		final String queueName1=NotyQueue.getQueue(NotyQueue.Index.ONE.getValue(),false,false).getName();
		final String queueName2=NotyQueue.getQueue(NotyQueue.Index.TWO.getValue(),false,false).getName();
		final String queueName3=NotyQueue.getQueue(NotyQueue.Index.THREE.getValue(),false,false).getName();
		final String queueName4=NotyQueue.getQueue(NotyQueue.Index.FOUR.getValue(),false,false).getName();
		final String queueName5=NotyQueue.getQueue(NotyQueue.Index.FIVE.getValue(),false,false).getName();
		final String queueName6=NotyQueue.getQueue(NotyQueue.Index.SIX.getValue(),false,false).getName(); 
		
		final String queueNameDevice0=NotyQueue.getQueue(NotyQueue.Index.ZERO.getValue(),true,false).getName();
		final String queueNameDevice1=NotyQueue.getQueue(NotyQueue.Index.ONE.getValue(),true,false).getName();
		final String queueNameDevice2=NotyQueue.getQueue(NotyQueue.Index.TWO.getValue(),true,false).getName();
		final String queueNameDevice3=NotyQueue.getQueue(NotyQueue.Index.THREE.getValue(),true,false).getName();
		final String queueNameDevice4=NotyQueue.getQueue(NotyQueue.Index.FOUR.getValue(),true,false).getName();
		final String queueNameDevice5=NotyQueue.getQueue(NotyQueue.Index.FIVE.getValue(),true,false).getName();
		final String queueNameDevice6=NotyQueue.getQueue(NotyQueue.Index.SIX.getValue(),true,false).getName(); 
		
		new NotyThread(threadPool0,queueName0).start();	
		new NotyThread(threadPool1,queueName1).start();
		//new NotyThread(threadPool2,queueName2).start();
		new NotyThread(threadPool3,queueName3).start();
		new NotyThread(threadPool4,queueName4).start();
		new NotyThread(threadPool5,queueName5).start();
		new NotyThread(threadPool6,queueName6).start();
		
		new NotyThread(threadPoolDevice0,queueNameDevice0).start();	
		new NotyThread(threadPoolDevice1,queueNameDevice1).start();
		new NotyThread(threadPoolDevice2,queueNameDevice2).start();
		//new NotyThread(threadPoolDevice3,queueNameDevice3).start();
		new NotyThread(threadPoolDevice4,queueNameDevice4).start();
		new NotyThread(threadPoolDevice5,queueNameDevice5).start();
		new NotyThread(threadPoolDevice6,queueNameDevice6).start();
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
						Thread.currentThread().sleep(2);
					} catch(InterruptedException ex){
						log.error("Thread be interrupted.",ex);
					}
					//处理正常的消息队列
					execute(threadPool,queueName);
					long time2=DateUtils.getCurTime();
					log.debug("notyServer cost time:"+(time2-time1));
					/*final Thread t=new Thread(new FamilyServer(port));
					log.info("平台 服务 启动......");
					t.start();
					Runtime.getRuntime().addShutdownHook(new Thread() {
						@Override
						public void run() { 
							t.interrupt();
						}
					});*/ 
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
			//log.info("notification value is"+value);
		}
		
		/*for(String s:values)
			log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->"+s);*/
		/*if(null==values.get(1)){
			//log.debug("notification in the queue is empty.");
			return;
		}*/
		threadPool.execute(new NotificationProcessor(value));
		long time3=DateUtils.getCurTime();
		log.debug("notyServer process cost time:"+(time3-time2));
	}
}