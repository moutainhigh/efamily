package com.winterframework.efamily.utils;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.base.utils.MD5Util;
import com.winterframework.modules.utils.SpringContextHolder;

public class TokenManager {
	private static final Logger log = LoggerFactory.getLogger(TokenManager.class); 
	private static final String PREFIX=Base64Util.getBASE64("TOKEN_".getBytes());
	private static final RedisClient redis=SpringContextHolder.getBean("RedisClient");
	/**
	 * key:token
	 * value:expire
	 */
	//private static final Map<String,Long> tokenMap = new ConcurrentHashMap<String,Long>();
	static{
		//手表测试userId：100001
		//tokenMap.put("22222222222222222222222222222222",100001L);
		//tokenMap.put("aaaaaaaabbbbbbbbccccccccdddddddd",613L);
		Context cc=new Context();
		/*cc.set("userId", 100001L);
		cc.set("deviceId", 1001L);
		redis.set(getRedisToken("22222222222222222222222222222222"),JsonUtils.toJson(cc));*/
		cc=new Context();
		cc.set("userId", 999999L);
		cc.set("deviceId", 999999L);
		redis.set(getRedisToken("aaaaaaaabbbbbbbbccccccccdddddddd"),JsonUtils.toJson(cc));
	}
    public static void echoTokenMap(){
    	/*for(Map.Entry<String,Long> entry:tokenMap.entrySet()){
    		log.info("tokenMap : key = "+entry.getKey()+" ; value = "+entry.getValue()+" ; ");
		}*/
    	Set<String> tokenSet=redis.keys(getTokenPattern());
		Context ctx=null;
		for(String token:tokenSet){
			String ctxStr=redis.get(token);
			ctx=JsonUtils.fromJson(ctxStr,Context.class);
			log.info("tokenInfo: key = "+token+" ; userId = "+ctx.getUserId()+" ; deviceId = "+ctx.getDeviceId()+" ; ");
		}
    }
	public static boolean isValid(Long userId,Long deviceId,String token){
		if(null==userId) return false;
		String tokenTmp=getToken(userId,deviceId);
		return null==tokenTmp?false:tokenTmp.equals(token);
	}
	
	public static String getTokenOld(Long userId,Long deviceId) { 
		if(null!=userId){
			Set<String> tokenSet=redis.keys(getTokenPattern());
			String tokenKey=getTokenKey(userId, deviceId);
			Context ctx=null;
			if(null!=tokenSet){
				for(String token:tokenSet){
					String ctxStr=redis.get(token);
					ctx=JsonUtils.fromJson(ctxStr,Context.class);
					String tokenKeyTmp=getTokenKey(ctx.getUserId(),ctx.getDeviceId());
					if(null!=tokenKeyTmp && tokenKeyTmp.equals(tokenKey)){
						return getRealToken(token);
					}
				}
			}
		}
		return null;
	}                    
	
	
	
	public static String getToken(Long userId,Long deviceId) { 
		String token = null;
		if(null!=userId){
			String tokenTemp = getMd5TokenKey(userId, deviceId);
			if(redis.get(tokenTemp)!=null){
				token = getRealToken(tokenTemp);
			}
			if(deviceId!=null && StringUtils.isBlank(token)){
				token = redis.get("DEVICE_OLD_DEVICE_"+userId+"_"+deviceId);
				if(StringUtils.isBlank(token)){
					token = getTokenOld(userId, deviceId);
					if(StringUtils.isNotBlank(token)){
						redis.set("DEVICE_OLD_DEVICE_"+userId+"_"+deviceId, token);
					}
				}
			}
		}
		
		return token;
	} 
	

	public static String getTokenForPush(Long userId,Long deviceId) { 
		String token = null;
		if(null!=userId){
			String tokenTemp = getMd5TokenKey(userId, deviceId);
			if(redis.get(tokenTemp)!=null){
				token = getRealToken(tokenTemp);
			}
			if(StringUtils.isBlank(token)){
				token = redis.get("DEVICE_OLD_DEVICE_"+userId+"_"+deviceId);
				if(StringUtils.isBlank(token)){
					token = getTokenOld(userId, deviceId);
					if(StringUtils.isNotBlank(token)){
						redis.set("DEVICE_OLD_DEVICE_"+userId+"_"+deviceId, token);
					}
				}
			}
		}
		
		return token;
	} 
	
	
	public static String getMd5TokenKey(Long userId,Long deviceId){
		if(null==userId) return null;
		StringBuilder sb = new StringBuilder();
		sb.append(userId);
		sb.append(Separator.vertical);
		if(null==deviceId){
			sb.append("");
		}else{
			sb.append(deviceId);
		}
		return PREFIX+MD5Util.getMD5Format(sb.toString().getBytes());
	}
	
	public static Context getTokenContext(String token) { 
		if(null==token) return null;
		String ctxStr=redis.get(getRedisToken(token));
		if(null!=ctxStr){
			return JsonUtils.fromJson(ctxStr, Context.class);
		}
		return null;
		//return tokenMap.get(token);
	}
	private static String getRedisToken(String token){
		return PREFIX+token;
	}
	private static String getRealToken(String redisToken){
		return redisToken.substring(PREFIX.length());
	}
	private static String getTokenPattern(){
		return PREFIX+Separator.start;
	}
	public static String save(Context ctx){  
		String token=getToken(ctx.getUserId(), ctx.getDeviceId());
		if(null==token){
			token=getMd5TokenKey(ctx.getUserId(), ctx.getDeviceId());
		  //token=getTokenValue();
		  //tokenMap.put(token,userId);
		  redis.set(token, JsonUtils.toJson(ctx));
		  token=getRealToken(token);
		}
		return token;
	} 
	public static String save(Context ctx,String token){  
		String tokenTemp=getToken(ctx.getUserId(), ctx.getDeviceId());
		if(null==tokenTemp){
			tokenTemp=getRedisToken(token);
		    redis.set(tokenTemp, JsonUtils.toJson(ctx));
		}
		return token;
	} 
	
	public static String remove(Long userId,Long deviceId){
		String token=getToken(userId, deviceId);
		remove(token);	
		return token;
	}
		
	public static void updateTokenForSwitchDevice(Long userIdA,Long deviceIdA,Long userIdB,Long deviceIdB,Map<String,String> data){
		String tokenA=getToken(userIdA, deviceIdA);
		String tokenB=getToken(userIdB, deviceIdB);
		Context contextA = getTokenContext(tokenA);
		Context contextB = getTokenContext(tokenB);
		remove(tokenA);	
		remove(tokenB);
		log.info("更换手表时更换TOKEN： userIdA = "+userIdA + " ; deviceIdA = "+deviceIdA+" ; userIdB = "+userIdB+" ; deviceIdB = "+deviceIdB+" ; contextA = "+contextA+" ; contextB = "+contextB);
		//*** A设备在线  需将此通道和设备一同给B用户
        if(userIdA.longValue()==userIdB.longValue()){
        	log.info("更换手表时，oprtype=1，增加一块新的手表：userIdA = userIdB = "+userIdA);
        	return;
        }
		
		if(contextA != null){
			if(contextB == null){
				contextB = new Context();
				//*** 添加其他数据  *** 
				contextB.set("nickName",data.get("passiveName"));
				contextB.set("phoneNumber",data.get("passivePhoneNumber"));
				contextB.set("familyName",data.get("passiveFamilyName"));
			}
			contextB.set("userId", userIdB);//B用户
			contextB.set("deviceId", deviceIdA);
			save( contextB,tokenA);
		}
		//*** B设备在线  需将此通道和设备一同给A用户
        if(contextB != null){
        	if(contextA == null){
				contextA = new Context();
				//*** 添加其他数据  *** 
				contextA.set("nickName",data.get("initiativeName"));
				contextA.set("phoneNumber",data.get("initiativePhoneNumber"));
				contextA.set("familyName",data.get("initiativeFamilyName"));
			}
			contextA.set("userId", userIdA);//A用户
			contextA.set("deviceId", deviceIdB);
			save( contextA,tokenB);
		}
	}
	public static void remove(String token){ 
		if(null==token) return;
		//tokenMap.remove(token);
		redis.del(getRedisToken(token));
	}
/*	private static String getTokenValue(){ 
		return PREFIX+UUID.randomUUID().toString().replace("-", "");
	}*/
	

	
	public static String getTokenKey(Long userId,Long deviceId){
		if(null==userId) return null;
		StringBuilder sb = new StringBuilder();
		sb.append(userId);
		sb.append(Separator.vertical);
		if(null==deviceId){
			sb.append("");
		}else{
			sb.append(deviceId);
		}
		return Base64Util.getBASE64(sb.toString().getBytes());
	}
	
	public static void main(String[] args){
		/*String token=UUID.randomUUID().toString().replace("-", "");//Base64.encodeBase64String(UUID.randomUUID().toString().toUpperCase().getBytes(CharsetFactory.getCharset(CharsetFactory.CharsetEnum.UTF8.getCode())));
		System.out.println(token);
		String s=getTokenKey(100101L, 1001L);
		System.out.println(s);
		System.out.println(new String(Base64Util.getBytesFromBASE64(s)));*/
		
		System.out.println(getTokenKey(2208L, 376L));
		
	}
	
}