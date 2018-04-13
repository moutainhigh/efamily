package com.winterframework.efamily.api.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IApiAccessManageService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EfApi;
import com.winterframework.efamily.entity.EfApiAccess;
import com.winterframework.efamily.entity.EfKey;
import com.winterframework.efamily.entity.EfKeyApi;
import com.winterframework.efamily.service.IEfComApiAccessService;
import com.winterframework.efamily.service.IEfComApiService;
import com.winterframework.efamily.service.IEfKeyApiService;
import com.winterframework.efamily.service.IEfKeyService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("apiAccessManageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ApiAccessManageServiceImpl implements IApiAccessManageService{
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient; 
	@PropertyConfig(value = "api.access.liveTime")
	private String liveTime;
	@PropertyConfig(value = "api.access.limitCount")
	private String limitCount;
	@Resource(name="efComApiAccessServiceImpl")
	private IEfComApiAccessService apiAccessService;
	@Resource(name="efComApiServiceImpl")
	private IEfComApiService apiService;
	@Resource(name="efKeyServiceImpl")
	private IEfKeyService efKeyService;
	
	@Resource(name="efKeyApiServiceImpl")
	private IEfKeyApiService efKeyApiServiceImpl;

	
	@Override
	public void apiAccessManage(String key, String url) throws BizException {
		Context ctx = new Context();
		ctx.set("userId", -1);
		EfKey efKey=efKeyService.getByKey(key);
		if(null==efKey){
			throw new BizException(ResultCode.KEY_INVALID.getCode());
		}
		EfApi efApi = apiService.getByUrl(ctx,url);
		if(efApi == null){
			throw new BizException(ResultCode.API_UNDEFINED.getCode());
		}
		EfKeyApi efKeyApi = efKeyApiServiceImpl.getByKey(key);
		if(efKeyApi == null){
			throw new BizException(ResultCode.CUSTOMER_UNPERMISSIONS.getCode());
		}
		List<String> permiss = Arrays.asList(efKeyApi.getApiIds().split(","));
		if(!permiss.contains(String.valueOf(efApi.getId()))){
			throw new BizException(ResultCode.CUSTOMER_UNPERMISSIONS.getCode());
		}
		if(!isAccessFrequentlyBetweenTime(ctx,url,key)){
			throw new BizException(ResultCode.API_ACCESS_FREQUENTLY.getCode());
		}
		this.saveOrUpdateApiAccess(ctx,key,efApi.getId());
	}
	
	private void saveOrUpdateApiAccess(Context ctx,String ukey,Long apiId)
			throws BizException {
		Date date = DateUtils.parse(DateUtils.format(new Date(), 
				DateUtils.DATETIME_WITHOUT_MINUTES_FORMAT_PATTERN), DateUtils.DATETIME_WITHOUT_MINUTES_FORMAT_PATTERN);
		
		EfApiAccess entity = new EfApiAccess();
		entity.setApiId(apiId);
		entity.setUkey(ukey);
		entity.setTime(date);
		EfApiAccess efApiAccess = apiAccessService.selectOneObjByAttribute(ctx, entity);
		if(efApiAccess == null){
			entity.setCount(1);
			apiAccessService.save(ctx, entity);
		}else{
			efApiAccess.setCount(efApiAccess.getCount()+1);
			apiAccessService.save(ctx, efApiAccess);
		}
	}

	private boolean isAccessFrequentlyBetweenTime(Context ctx,String url, String ukey)
			throws BizException {
		String key = ukey+url;
		int liveTimei = Integer.valueOf(liveTime)*60;
		try {
			String counts = redisClient.get(key);
			Long expire = redisClient.getExpireTime(key);
			
			if(counts == null){
				redisClient.set(key, "1", liveTimei);
				return true;
			}else{
				long count = Long.valueOf(counts);
				if(count+1>Long.valueOf(limitCount).longValue()){
					return false;
				}else{
					counts = String.valueOf(count+1);
					redisClient.set(key, counts, expire.intValue());
					return true;
				}
			}
		} catch (Exception e) {
			throw new BizException(ResultCode.UNKNOWN.getCode());
		}
		
	}

	
	
}
