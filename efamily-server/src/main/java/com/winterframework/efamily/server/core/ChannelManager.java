package com.winterframework.efamily.server.core;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.modules.utils.SpringContextHolder;

public class ChannelManager {
	private static final Logger log = LoggerFactory.getLogger(ChannelManager.class);
	private static final Map<String, Channel> channelMap = new ConcurrentHashMap<String,Channel>();
	//private final static ThreadLocal<Channel> channels = new ThreadLocal<Channel>(){};
	//private static final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue");
	//private static final RedisClient redisClient=SpringContextHolder.getBean("RedisClient");
	//private static final String queueOnline="queue_online";
	
	public static void echoChannelManager(){ 
		for(Map.Entry<String, Channel> entry:channelMap.entrySet()){
				log.error("****************************************** echoChannelManager******************************************");
				log.error(" ChannelManager: key = "+entry.getKey()+" ; value = "+entry.getValue()+" ; base64 = "+new String(Base64Util.getBytesFromBASE64(entry.getKey())));
				log.error("****************************************** echoChannelManager******************************************");
		}
	}

	public static String getChannelMapUsers(){
		StringBuffer sb = new StringBuffer();
		Set<String> keys = channelMap.keySet();
		
		for(String key:keys){
			String keyTemp = new String(Base64Util.getBytesFromBASE64(key));
			keyTemp = keyTemp.split(Separator.verticalSep)[0];
			sb.append(Separator.comma);
			sb.append(keyTemp);
		}
		
		if(sb.length()>0){
			return sb.substring(1);
		}
		return sb.toString();
	}
	
	public static Channel get(Long userId, Long deviceId) {
		if(null==userId) return null;
		String tokenKey=TokenManager.getTokenKey(userId, deviceId);
		return channelMap.get(tokenKey);
	} 
	public static Channel get(String key) {
		if(null==key) return null;
		return channelMap.get(key);
	} 
	public static void save(Long userId, Long deviceId, Channel channel){   
		if(null!=userId){
			String tokenKey=TokenManager.getTokenKey(userId,deviceId);
			if(null==channelMap.get(tokenKey)){	 
				log.error("User connect. userId="+userId); 
				channelMap.put(tokenKey, channel);
				//redisQueue.del(queueOnline, getOnlineValue(userId, deviceId));
				//redisQueue.add(queueOnline, getOnlineValue(userId, deviceId));
			}
		}
	}
	public static void saveForChannelSwitch(Long userId, Long deviceId,String token, Channel channel){   
		if(null!=userId){
			log.info("switch channel.");
			String tokenKey=TokenManager.getTokenKey(userId,deviceId);
			log.info("get  old token.");
			String tokenOld=TokenManager.getToken(userId,deviceId);
			if(null==channelMap.get(tokenKey) || (token != null && token.equals(tokenOld)) ){	 
				log.error("User connect. userId="+userId); 
				channelMap.put(tokenKey, channel);
				log.info("put new channel.");
				//redisQueue.del(queueOnline, getOnlineValue(userId, deviceId));
				//redisQueue.add(queueOnline, getOnlineValue(userId, deviceId));
			}
		}
	}
	public static void save(String key,Channel channel){
		if(null!=key){
			if(null==channelMap.get(key) || !get(key).equals(channel)){
				log.error("device connect. imei="+key); 
				channelMap.put(key, channel);
			}
		}
	}
	private static String getOnlineValue(Long userId, Long deviceId){
		StringBuilder sb = new StringBuilder();
		sb.append(userId);
		sb.append(Separator.vertical);
		if(null==deviceId){
			sb.append("");
		}else{
			sb.append(deviceId);
		} 
		return sb.toString();
	}
	public static void remove(Long userId,Long deviceId){ 
		if(null==userId) return;
		String tokenKey=TokenManager.getTokenKey(userId,deviceId);
		channelMap.remove(tokenKey);
		//redisQueue.del(queueOnline, getOnlineValue(userId, deviceId));
	}

	/**
	 * 功能：更换设备时更换通道  
	 * @param userIdA
	 * @param deviceIdA
	 * @param userIdB
	 * @param deviceIdB
	 */
 	public static void updateChannelForSwitchDevice(Long userIdA,Long deviceIdA,Long userIdB,Long deviceIdB){
		Channel channelA = get(userIdA,deviceIdA);
		Channel channelB = get(userIdB,deviceIdB);
		remove(userIdA,deviceIdA);
		remove(userIdB,deviceIdB);
	    log.info("更换设备时更换通道  : userIdA = "+userIdA+" ; userIdB = "+userIdB+" ; channelA = "+channelA+" ; channelB = "+channelB);
        if(userIdA.longValue()==userIdB.longValue()){
        	log.info("更换手表通道时，oprtype=1，增加一块新的手表：userIdA = userIdB = "+userIdA);
        	return;
        }
	    if( userIdA!=null  && channelB != null && deviceIdB!=null){
			save(userIdA,deviceIdB,channelB);
		}
		if( userIdB!=null  && channelA != null && deviceIdA!=null){
			save(userIdB,deviceIdA,channelA);
		}
	}
	public static void remove(Channel channel){ 
		for(Map.Entry<String, Channel> entry:channelMap.entrySet()){
			if(entry.getValue()==channel){
				log.error("User disconnect. userId="+new String(Base64Util.getBytesFromBASE64(entry.getKey())));
				channelMap.remove(entry.getKey());
			}
		}
	}
	public static String getBy(Channel channel){ 
		for(Map.Entry<String, Channel> entry:channelMap.entrySet()){
			if(entry.getValue()==channel){
				return entry.getKey();
			}
		}
		return null;
	}
	public static boolean isConnected(Long userId,Long deviceId){
		//if(null!=redisClient.get("test"+userId)) return true;
		Channel c=get(userId, deviceId);
		return null!=c && c.isActive();
	}	
	public static boolean isConnected(String key){
		//if(null!=redisClient.get("test"+userId)) return true;
		Channel c=get(key);
		return null!=c && c.isActive();
	}
	public static boolean isConnected(Channel channel){
		return null!=channel && channel.isActive();
	}
}
