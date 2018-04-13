package com.winterframework.efamily.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IEfApiLimitService;
import com.winterframework.efamily.api.service.IEfApiService;
import com.winterframework.efamily.api.service.IEfKeyApiLimitService;
import com.winterframework.efamily.api.service.IEfKeyIpService;
import com.winterframework.efamily.api.service.ILoadCacheFromDatabaseService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EfApi;
import com.winterframework.efamily.entity.EfApiLimit;
import com.winterframework.efamily.entity.EfKey;
import com.winterframework.efamily.entity.EfKeyApi;
import com.winterframework.efamily.entity.EfKeyApiLimit;
import com.winterframework.efamily.service.IEfKeyApiService;
import com.winterframework.efamily.service.IEfKeyService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.modules.utils.SpringContextHolder;

@Service("loadCacheFromDatabaseServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class LoadCacheFromDatabaseServiceImpl implements ILoadCacheFromDatabaseService{

	private final RedisClient redisClient=SpringContextHolder.getBean("RedisClient");
	
	//private final static String KEY ="key";
	private final static String AUTH_LOAD_CONFIG ="auth_load_config";
	private final static String AUTH_URL_ ="auth_url_";
	private final static String AUTH_KEY_API_ ="auth_key_api_";
	private final static String AUTH_KEY_ ="auth_key_";
	private final static String AUTH_API_LIMIT_MINUTE ="auth_api_limit_minute";
	private final static String AUTH_KEY_API_LIMIT_MINUTE ="auth_key_api_limit_minute";
	private final static String AUTH_KEY_API_LIMIT_DAY ="auth_key_api_limit_day";
	
	@Resource(name = "efApiServiceImpl")
	private IEfApiService efApiServiceImpl;
	
	@Resource(name = "efKeyServiceImpl")
	private IEfKeyService efKeyServiceImpl;
	
	@Resource(name = "efKeyApiServiceImpl")
	private IEfKeyApiService efKeyApiServiceImpl;
	
	@Resource(name = "efApiLimitServiceImpl")
	private IEfApiLimitService efApiLimitServiceImpl;
	
	@Resource(name = "efKeyApiLimitServiceImpl")
	private IEfKeyApiLimitService efKeyApiLimitServiceImpl;
	
	@Resource(name = "efKeyIpServiceImpl")
	private IEfKeyIpService efKeyIpServiceImpl;
	
	@Resource(name = "ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService;
	
	private static final Logger log = LoggerFactory.getLogger(LoadCacheFromDatabaseServiceImpl.class);
	
	private String default_iKey = "11111111111111111111111111111111";
	
	
	public ResultCode authAndFrequencyLimitCheck(String key,String requestUri,String ip) throws Exception{
		ResultCode resultCode = ResultCode.OK;
		 String apiId = redisClient.get(AUTH_URL_+requestUri);
         String apiIdAccessAuth = redisClient.get(AUTH_KEY_API_+key);
     	if(!redisClient.exists(AUTH_KEY_+key)){
     		//KEY验证失败
     		resultCode = ResultCode.KEY_INVALID; 
     		
     	}else if(StringUtils.isBlank(apiId)){
     		//URL不存在或者未对外开放
     		resultCode = ResultCode.API_UNDEFINED; 
     		
     	}else if(StringUtils.isBlank(apiIdAccessAuth)){
     		String apiIdAccessAuthDefault = redisClient.get(AUTH_KEY_API_+default_iKey);
     		if(StringUtils.isBlank(apiIdAccessAuthDefault)||!apiIdAccessAuthDefault.contains(","+apiId+",")){
	     		//没有权限访问此API
	     		resultCode = ResultCode.NO_AUTH_ACCESS_URL; 
     		}
     		
     	}else if(!StringUtils.isBlank(apiIdAccessAuth)){
     		if(!apiIdAccessAuth.contains(","+apiId+",")){
     			//没有权限访问此API
         		resultCode = ResultCode.NO_AUTH_ACCESS_URL; 
     		}
     	}
     	else if(!checkRequestRateLimit(AUTH_API_LIMIT_MINUTE,apiId,60)){
     		//当前API 超过访问频率
     		resultCode = ResultCode.API_ACCESS_FREQUENTLY; 
     		
     	}else if(!checkRequestRateLimit(AUTH_KEY_API_LIMIT_MINUTE,apiId,key,60)){
     		//当前KEY 对应的  API 超过访问频率
     		resultCode = ResultCode.API_MINUTE_ACCESS_FREQUENTLY; 
     		
     	}else if(!checkRequestRateLimit(AUTH_KEY_API_LIMIT_DAY,apiId,key,DateUtils.getCurrentRemainingSeconds())){
     		//当前KEY 对应的  API 超过当天的访问限制
     		resultCode = ResultCode.API_DAY_ACCESS_FREQUENTLY; 
     		
     	}
		
		return resultCode;
	}
	
	public void loadAuthAndFrequencyFromData() throws BizException{
    	log.info("[loadAuthAndFrequencyFromData]**********************  加载权限和频率限制 START    ********************************");
    	//*** 如果启动或者重启时  不需要重新加载缓存 则将标识设置为 100
    	if("100".equals(redisClient.get(AUTH_LOAD_CONFIG))){
    		log.info("标识为 100,启动或者重启时  不需要重新加载缓存!");
    		return;
    	}
    	//*** 加载前清空缓存  ***
    	deleteAuthAndFrequencyCache();
    	//*** 加载标识设置
    	redisClient.set(AUTH_LOAD_CONFIG, "0");
    	//*** 加载 key
    	List<EfKey> efKeyList = efKeyServiceImpl.selectListObjByAttribute(new Context(), new EfKey());
    	for(EfKey efKey:efKeyList){
    		redisClient.set(AUTH_KEY_+efKey.getUkey(), efKey.getCustomerId()+"");
    	}
    	redisClient.set(AUTH_LOAD_CONFIG, "1");
    	//*** 加载api 对应的url
    	List<EfApi> efApiList = efApiServiceImpl.selectListObjByAttribute(new Context(), new EfApi());
    	for(EfApi efApi:efApiList){
    		redisClient.set(AUTH_URL_+efApi.getUrl(), efApi.getId()+"");
    	}
    	redisClient.set(AUTH_LOAD_CONFIG, "2");
    	//*** 加载key 对应api 的权限
    	List<EfKeyApi> efKeyApiList = efKeyApiServiceImpl.selectListObjByAttribute(new Context(), new EfKeyApi());
    	for(EfKeyApi efKeyApi:efKeyApiList){
    		redisClient.set( AUTH_KEY_API_+efKeyApi.getUkey(), ","+efKeyApi.getApiIds()+"," );
    	}
    	redisClient.set(AUTH_LOAD_CONFIG, "3");
    	//*** 加载api 频率限制
    	List<EfApiLimit> efApiLimitList = efApiLimitServiceImpl.selectListObjByAttribute(new Context(), new EfApiLimit());
    	for(EfApiLimit efApiLimit:efApiLimitList){
    		redisClient.set( AUTH_API_LIMIT_MINUTE+"_max_"+efApiLimit.getApiId(), efApiLimit.getDayReqLimit()+"");
    	}
    	redisClient.set(AUTH_LOAD_CONFIG, "4");
    	//*** 加载key 对应api 频率限制
    	//*** 加载key 对应api 当天总量限制
    	List<EfKeyApiLimit> efKeyApiLimitList = efKeyApiLimitServiceImpl.selectListObjByAttribute(new Context(), new EfKeyApiLimit());
    	for(EfKeyApiLimit efKeyApiLimit:efKeyApiLimitList){
    		redisClient.set( AUTH_KEY_API_LIMIT_DAY+"_max_"+efKeyApiLimit.getUkey()+"_"+efKeyApiLimit.getApiId(), efKeyApiLimit.getDayReqLimit()+"");
    		redisClient.set( AUTH_KEY_API_LIMIT_MINUTE+"_max_"+efKeyApiLimit.getUkey()+"_"+efKeyApiLimit.getApiId(), efKeyApiLimit.getDayReqLimit()+"");
    	}
    	redisClient.set(AUTH_LOAD_CONFIG, "99");
    	log.info("[loadAuthAndFrequencyFromData]**********************  加载权限和频率限制  END    ********************************");
    }
	
    public void deleteAuthAndFrequencyCache(){
    	//*** 清除缓存
    	redisClient.deleteKeyByPrefix("auth_*");
    }
    
    public boolean checkRequestRateLimit(String prefix,String apiId,String key,int expireSeconds){
    	boolean flag = true;
    	int apiKeyDayRequestRateMax = 0;
    	String rateMax = redisClient.get(prefix+"_"+"max"+"_"+key+"_"+apiId);
    	if(rateMax == null){
    		apiKeyDayRequestRateMax = Integer.valueOf(redisClient.get(prefix+"_"+"max"+"_"+default_iKey+"_"+apiId));
    	}else{
    		apiKeyDayRequestRateMax = Integer.valueOf(rateMax);
    	}
    	//int apiKeyDayRequestRateMax = Integer.valueOf(redisClient.get(prefix+"_"+"max"+"_"+key+"_"+apiId));
    	String requestRate = redisClient.get(prefix+"_"+key+"_"+apiId);
    	if(StringUtils.isNotBlank(requestRate)){
    		if( Integer.valueOf(requestRate)+1>apiKeyDayRequestRateMax ){
        		flag = false ;
    		}else{
    			redisClient.set(prefix+"_"+key+"_"+apiId, String.valueOf(Integer.valueOf(requestRate)+1));
    		}
    	}else{
    		redisClient.set(prefix+"_"+key+"_"+apiId,"1",expireSeconds);
    	}
    	return flag;
    }
    
    public boolean checkRequestRateLimit(String prefix,String apiId,int expireSeconds){
    	boolean flag = true;
    	String rateMax = redisClient.get(prefix+"_"+"max"+"_"+apiId);
    	int apiKeyDayRequestRateMax = Integer.valueOf(rateMax);
    	String requestRate = redisClient.get(prefix+"_"+apiId);
    	if(StringUtils.isNotBlank(requestRate)){
    		if( Integer.valueOf(requestRate)+1>apiKeyDayRequestRateMax ){
        		flag = false ;
    		}else{
    			redisClient.set(prefix+"_"+apiId, String.valueOf(Integer.valueOf(requestRate)+1));
    		}
    	}else{
    		redisClient.set(prefix+"_"+apiId,"1",expireSeconds);
    	}
    	return flag;
    }
    
}
