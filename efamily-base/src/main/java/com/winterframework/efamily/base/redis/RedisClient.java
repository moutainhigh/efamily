package com.winterframework.efamily.base.redis;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("RedisClient")
public class RedisClient {

	@PropertyConfig(value = "redis1.ip")
	private String redisIp;
	@PropertyConfig(value = "redis.port")
	private Integer redisPort;
	@PropertyConfig(value = "redis.psword")
	private String redisPsword;
	@PropertyConfig(value = "redis.pool.maxActive")
	private Integer maxActive;
	@PropertyConfig(value = "redis.pool.maxIdle")
	private Integer maxIdle;
	@PropertyConfig(value = "redis.pool.maxWait")
	private Integer maxWait;
	@PropertyConfig(value = "redis.pool.testOnBorrow")
	private Boolean testOnBorrow;
	@PropertyConfig(value = "redis.pool.testOnReturn")
	private Boolean testOnReturn;

	private JedisPool jedisPool;

	public RedisClient() {
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
		jedisPool = new JedisPool(config,redisIp,redisPort,2000,redisPsword,0);
		//尝试获取一下数据，检验是否http超时
		this.ping();

	}

	/**
	 * 获取redis value (String)
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			value = instance.get(key);
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
	 * 获取redis value (String)
	 * @param key
	 * @return
	 */
	public Long getIncre(String key) {
		Long value = 0L;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			value = instance.incr(key);
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
	public void del(String ... keys) {

		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			instance.del(keys);
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
     * 删除匹配的key<br> 
     * 如以my为前缀的则 参数为"my*" 
     * @param key 
     */  
    public  void deleteKeys(String pattern){  
        //列出所有匹配的key  
    	Jedis instance = null;
    	instance = jedisPool.getResource();
        Set<String> keySet = instance.keys(pattern);  
        if(keySet == null || keySet.size()<=0){  
            return;  
        }  
        String keyArr[] = new String[keySet.size()];  
        int i =0;  
        for (String keys : keySet) {  
            keyArr[i] = keys;  
            i++;  
        }  
        del(keyArr);
    }  
      
    /** 
     * 删除前缀为{参数}的所有key<br> 
     * @param prefix 
     */  
    public void deleteKeyByPrefix(String prefix){  
        deleteKeys(prefix+"*");  
    }  
      
      
    /** 
     * 删除包含{参数}的所有key<br> 
     * @param contain 
     */  
    public void deleteKeyByContain(String contain){  
        deleteKeys("*"+contain+"*");  
    }  
    
	/**
	 * 添加key value 并且设置存活时间
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, int liveTime) {
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			instance.set(key, value);
			instance.expire(key, liveTime);
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
	 * 添加key value
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {

		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			instance.set(key, value);
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
	 * 通过正则匹配keys
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {

		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			return instance.keys(pattern);
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

	/**
	 * 检查key是否已经存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key) throws Exception {

		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			return instance.exists(key);
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
	 * 清空redis 所有数据
	 * @return
	 */
	public String flushDB() {

		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			return instance.flushDB();
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

	/**
	 * 查看redis里有多少数据
	 */
	public long dbSize() {

		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			return instance.dbSize();
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
		return 0;
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
	
	/**
	 * 锁key
	 * @param key
	 * @param expireTime 锁过期秒数
	 * @return
	 */
	public boolean lock(String key,int expireTime){
		int millSecds=expireTime;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			//如果set和expire 未在一个事务内完成 会出现锁持续存在  2.6版本以上可以调用set取代
			instance = jedisPool.getResource();
			Long curTime=DateUtils.getCurTime();
			if(1==instance.setnx(key, curTime+"").intValue()){
				instance.expire(key, millSecds);
				return true;
			}else{
				//抢锁失败  或者  锁过期无法自解   如果上述的expire失败 则自解锁
				String value=instance.get(key);
				if(null!=value){
					if(curTime>(Long.valueOf(value)+millSecds*1000)){  //如果已过期
						instance.set(key, curTime+"");
						instance.expire(key, millSecds);
						return curTime.equals(Long.valueOf(instance.get(key)));	//如果值不相等 则存在并发 抢锁失败
					}
				}else{	//此时已解锁
					if(1==instance.setnx(key, curTime+"").intValue()){
						instance.expire(key, millSecds); //5minutes
						return true;
					}
					//如果还是失败 那就不存在锁过期无法自解的问题而是并发抢锁失败
				}
			}
			return false;
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
	 * 解锁key
	 * @param key
	 */
	public void unlock(String key){
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			instance.del(key);
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

}
