package com.winterframework.efamily.ccontrol.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;

/**
 * 异步服务 --独立成单独项目服务 
 * @ClassName
 * @Description
 * @author ibm
 * 2016年6月1日
 */
@Service
public class AsyncServer {
	private static final Logger log = LoggerFactory.getLogger(AsyncServer.class); 
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(5);
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	
	//@PostConstruct
	public void startup(){
		log.info("异步 服务 启动......");
		
		new AsyncThread(threadPool).start();
	}
	
	private class AsyncThread extends Thread{
		ExecutorService threadPool;
		public AsyncThread(ExecutorService threadPool){
			this.threadPool=threadPool;
		}
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			while(true){
				try {
					Thread.currentThread().sleep(2);
				} catch(InterruptedException ex){
					log.error("Thread be interrupted.",ex);
				}
				try{
					//execute(threadPool,AsyncTask.SERVER_LOAD);
				}catch(Exception e){
					log.error("error occurs when write load capacity.",e);
				}
			}
		} 
	}
	private void execute(ExecutorService threadPool,final String taskName) {
		threadPool.execute(new Runnable() {
			public void run() {
				String serverIp=redisQueue.get(taskName);
				
				/*String loadStr=redisClient.get(RedisPrefix.SERVER+serverIp);
				Integer loadCount=(loadStr!=null)?Integer.valueOf(loadStr):0;
				redisClient.set(RedisPrefix.SERVER+serverIp, loadCount+1+"");*/
			}
		});
	}
}