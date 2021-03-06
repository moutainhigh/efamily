package com.winterframework.efamily.notification.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.notification.enums.QueuePrefix;
import com.winterframework.efamily.notification.model.NotyQueue;

@Service
public class NotyNewServer {
	private static final Logger log = LoggerFactory.getLogger(NotyNewServer.class); 
	private static final int multiply=5;
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(80*multiply);
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	
	@PostConstruct
	public void startup(){
		log.info("消息 服务 启动......");
		final String queueName=NotyQueue.QUEUE_USER; 
		
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
		String queueNameTarget=redisQueue.getUnique(queueName); 	//需改进成get rem?????
		long time2=DateUtils.getCurTime();
		log.debug("notyServer redis cost time:"+(time2-time1));
		/*String targetStr=redisClient.get(QueuePrefix.NOTY_PRE+queueNameTarget);
		if(null==targetStr || "".equals(targetStr)){
			targetStr=queueNameTarget;
			redisClient.set(QueuePrefix.NOTY_PRE+queueNameTarget, targetStr);
		}*/
		//List<String> values=redisQueue.get(queueName,queueName + "_offline",queueName+"_offline_1");  
		if(null==queueNameTarget){
			//log.info("notification in the queue all has been pushed.");
			return;
		}else{
			NotyTarget target=getTarget(queueNameTarget);
			/*//未在线 扔队尾	有要求离线也需要推送
			if(!ChannelManager.isConnected(target.getUserId(), target.getDeviceId())){
				redisQueue.add(queueName, queueNameTarget);
				return;
			}*/
			if(!redisClient.lock(QueuePrefix.NOTY_LOCK+queueNameTarget, 300)){	//5分钟
				addQueueUser(queueNameTarget);
				return;
			}
			threadPool.execute(new NotificationProcessorNew2(queueNameTarget));
		}
		long time3=DateUtils.getCurTime();
		log.debug("notyServer process cost time:"+(time3-time2));
	}
	private void addQueueUser(String queueNameTarget){
		//log.info("this is offline message add :"+NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
		redisQueue.add(NotyQueue.QUEUE_USER,queueNameTarget);
	}
	private NotyTarget getTarget(String queueNameTarget){
		if(null==queueNameTarget) return null;
		String targetStr=queueNameTarget.split(Separator.escape+Separator.underline)[1]; 
		String[] targetArr=targetStr.split(Separator.escape+Separator.vertical);
		Long userId=Long.valueOf(targetArr[0]);
		Long deviceId=targetArr.length==2?Long.valueOf(targetArr[1]):null;
		
		return new NotyTarget(userId,deviceId);
	}
}