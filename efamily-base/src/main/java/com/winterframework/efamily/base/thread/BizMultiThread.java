package com.winterframework.efamily.base.thread;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.modules.utils.SpringContextHolder;
  
public abstract class BizMultiThread{
	private static final Logger log = LoggerFactory.getLogger(BizMultiThread.class);
	private final RedisClient redisClient=SpringContextHolder.getBean("RedisClient");
	private ExecutorService threadPool;
	private String lockKey;
	private int lockSecds;
	private boolean needLock;
	public BizMultiThread(ExecutorService threadPool,String lockKey,int lockSecds){
		this.threadPool=threadPool;
		this.lockKey=lockKey;
		this.lockSecds=lockSecds;
		this.needLock=true;
	}
	public BizMultiThread(ExecutorService threadPool){
		this.threadPool=threadPool;
		this.needLock=false;
	}
	public void start() {
		if(needLock){
			if(!redisClient.lock(lockKey, lockSecds)){
				return;	//拿锁失败 说明前一个任务执行中 则本次不执行
			}
		}
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {  
					doBiz();
				} catch (Exception e) {
					log.error("业务处理异常:"+lockKey,e);
				} 
				if(needLock){
					//解锁
					redisClient.unlock(lockKey);
				}
			}
		});
	}
	protected abstract void doBiz() throws BizException;
}