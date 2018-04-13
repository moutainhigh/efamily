package com.winterframework.efamily.base.redis;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("redisQueue")
public class RedisQueue {
	private static final Logger log = LoggerFactory.getLogger(RedisQueue.class);
	
	@PropertyConfig(value = "redis.queue.ip")
	private String redisIp;
	@PropertyConfig(value = "redis.queue.port")
	private Integer redisPort;
	@PropertyConfig(value = "redis.queue.psword")
	private String redisPsword;
	@PropertyConfig(value = "redis.queue.pool.maxActive")
	private Integer maxActive;
	@PropertyConfig(value = "redis.queue.pool.maxIdle")
	private Integer maxIdle;
	@PropertyConfig(value = "redis.queue.pool.maxWait")
	private Integer maxWait;
	@PropertyConfig(value = "redis.queue.pool.testOnBorrow")
	private Boolean testOnBorrow;
	@PropertyConfig(value = "redis.queue.pool.testOnReturn")
	private Boolean testOnReturn;

	private JedisPool jedisPool;
	private final int MAX_COUNT=999999999;

	public RedisQueue() {
	}

	@PostConstruct
	public void initialPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		config.setMaxWait(maxWait);
		config.setMaxActive(maxActive);
		/*config.setTestWhileIdle(true);
		config.setTimeBetweenEvictionRunsMillis(30000);*/
		//jedisPool = new JedisPool(config, redisIp);
		jedisPool = new JedisPool(config,redisIp,redisPort,2000,redisPsword,0);
		//尝试获取一下数据，检验是否http超时
		this.ping();

	}

	/**
	 * 入队列
	 * @param queueName
	 * @param data
	 */
	public void add(String queueName,String data){
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			instance.rpush(queueName, data);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
			throw e;
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
	} 
	public String set(String queueName,int index,String value) {
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			long time1=DateUtils.getCurTime();
			instance = jedisPool.getResource();
			long time2=DateUtils.getCurTime();
			log.debug("redis get resource time:"+(time2-time1));
			synchronized(queueName){
				long time3=DateUtils.getCurTime();
				log.debug("redis get queue time:"+(time3-time2));
				value = instance.lset(queueName, index, value);
				long time4=DateUtils.getCurTime();
				log.debug("redis oper time:"+(time4-time3));
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}
	/**
	 * 设置队列超时 秒数
	 * @param queueName
	 * @param expireTime
	 */
	public void expire(String queueName,int expireTime){
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			instance.expire(queueName, expireTime);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
			throw e;
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
	} 
	/**
	 * 获取超时时间
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Long getExpireTime(String key) throws Exception {
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			return instance.ttl(key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
			throw e;
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
	}
	/**
	 * 入队首
	 * @param queueName
	 * @param data
	 */
	public void addHead(String queueName,String data){
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				instance.lpush(queueName, data);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
			throw e;
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
	} 
	/**
	 * 查看队列长度 (小于5000)
	 * @param queueName
	 * @return
	 */
	public int len(String queueName){
		Long value = 0L;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				value = instance.llen(queueName);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value.intValue();
	}
	public List<String> get(String... queueNames) {
		List<String> value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			long time1=DateUtils.getCurTime();
			instance = jedisPool.getResource();
			long time2=DateUtils.getCurTime();
			log.debug("redis b get resource time:"+(time2-time1));
			synchronized(getAllQueueName(queueNames)){
				long time3=DateUtils.getCurTime();
				log.debug("redis b get queue time:"+(time3-time2));
				value = instance.blpop(1,queueNames);
				long time4=DateUtils.getCurTime();
				log.debug("redis b oper time:"+(time4-time3));
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}
	
	public String get(String queueName) {
		String value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			long time1=DateUtils.getCurTime();
			instance = jedisPool.getResource();
			long time2=DateUtils.getCurTime();
			log.debug("redis get resource time:"+(time2-time1));
			synchronized(queueName){
				long time3=DateUtils.getCurTime();
				log.debug("redis get queue time:"+(time3-time2));
				value = instance.lpop(queueName);
				long time4=DateUtils.getCurTime();
				log.debug("redis oper time:"+(time4-time3));
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}
	public String getUnique(String queueName) {
		String value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try { 
			instance = jedisPool.getResource(); 
			synchronized(queueName){
				value = instance.lpop(queueName);
				if(null!=value){
					instance.lrem(queueName,MAX_COUNT, value);
				}
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}
	public List<String> get(String queueName,int fromIndex,int toIndex) {
		List<String> value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				value = instance.lrange(queueName, fromIndex, toIndex);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}
	public String get(String queueName,int index) {
		String value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				value = instance.lindex(queueName, index);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}
	
	/**
	 * 通过key删除
	 * @param key
	 */
	public void del(String queueName,String value) {

		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				instance.lrem(queueName, 1, value);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
	}
	/**
	 * 检查是否连接成功
	 * @return
	 */
	public String ping() {
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			return instance.ping();
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return null;
	}
	
	private String getAllQueueName(String...queueNames){
		if(null!=queueNames){
			StringBuilder sb=new StringBuilder();
			for(String queueName:queueNames){
				sb.append(queueName);
			}
			return sb.toString();
		}
		return "";
	}
}
