package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.thread.BizMultiThread;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.RedisPrefix;
import com.winterframework.efamily.entity.EfCustomerDataSendSetting;
import com.winterframework.efamily.entity.EfDeviceHealthyDataPush;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceHealthyDataPushService;
import com.winterframework.efamily.service.IEfComCustomerDataSendSettingService;
import com.winterframework.efamily.service.IEfComDeviceHealthyDataPushService;
import com.winterframework.efamily.utils.HttpUtil;


@Service("deviceHealthyDataPushServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceHealthyDataPushServiceImpl  implements IDeviceHealthyDataPushService {
	private Logger log = LoggerFactory.getLogger(DeviceHealthyDataPushServiceImpl.class);
	
	@Resource(name="efComDeviceHealthyDataPushServiceImpl")
	protected IEfComDeviceHealthyDataPushService efComDeviceHealthyDataPushServiceImpl;

	@Resource(name="efComCustomerDataSendSettingServiceImpl")
	protected IEfComCustomerDataSendSettingService efComCustomerDataSendSettingServiceImpl;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	
	@Resource(name="httpUtil")
	protected HttpUtil httpUtil;

 
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(80);
	private final int beforeMin=24*60;
	
	public void doProcess() throws BizException {
		
		String alarmLastTime=redisClient.get(RedisPrefix.HEALTH_DEVICEHEALTHYDATA_PUSH_LASTTIME);
		Date lastTime=null;
		Date curTime=DateUtils.currentDate();
		if(null!=alarmLastTime){
			lastTime=DateUtils.getDate(Long.valueOf(alarmLastTime));
		}else{
			lastTime=DateUtils.addMinutes(curTime, -1*beforeMin);
		}
		
		int status = 0; //未处理
		List<EfDeviceHealthyDataPush> efDeviceHealthyDataPushList = null;
		try {
			efDeviceHealthyDataPushList = efComDeviceHealthyDataPushServiceImpl.getDeviceHealthyDataPushList(lastTime, curTime, status);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询推送健康数据时 出现异常： lastTime ="+lastTime+" ; curTime = "+curTime);
		}
		
		if(null!=efDeviceHealthyDataPushList){
			for(final EfDeviceHealthyDataPush efDeviceHealthyDataPush:efDeviceHealthyDataPushList){
				new BizMultiThread(threadPool) {
					protected void doBiz() throws BizException {
						process(efDeviceHealthyDataPush);
					};
				}.start();
			}
		}
		redisClient.set(RedisPrefix.HEALTH_DEVICEHEALTHYDATA_PUSH_LASTTIME, curTime.getTime()+"");
	}
	
	
	public void process(EfDeviceHealthyDataPush efDeviceHealthyDataPush){
		try{
			int status=EfDeviceHealthyDataPush.Status.INIT.getValue();
			try{
				addCall(efDeviceHealthyDataPush);
				status=EfDeviceHealthyDataPush.Status.SUCCESS.getValue();
			}catch(Exception e){
				log.error("首次发送健康数据出现异常 efDeviceHealthyDataPush ："+efDeviceHealthyDataPush.toString(),e);
				try{
					addCall(efDeviceHealthyDataPush);
				}catch(Exception e2){
					status=EfDeviceHealthyDataPush.Status.FAILED.getValue();
					log.error("首次重发健康数据出现异常 efDeviceHealthyDataPush ："+efDeviceHealthyDataPush.toString(),e);
				}
			}
			efDeviceHealthyDataPush.setStatus(status);
			Context ctx=new Context();
			ctx.set("userId",-1);
			efComDeviceHealthyDataPushServiceImpl.save(ctx, efDeviceHealthyDataPush);
		}catch(BizException e){
			log.error("发送健康数据出现异常 efDeviceHealthyDataPush ："+efDeviceHealthyDataPush.toString(),e);
		}
		
	}

	
	/**
	 * 考虑 单独项目请求第三方
	 * @param efDeviceHealthyDataPush
	 * @throws BizException
	 */
	protected void addCall(EfDeviceHealthyDataPush efDeviceHealthyDataPush) throws BizException{
		Long customerId=efDeviceHealthyDataPush.getCustomerId();
		Integer dataType = efDeviceHealthyDataPush.getType();
		EfCustomerDataSendSetting efCustomerDataSendSetting = efComCustomerDataSendSettingServiceImpl.getEfCustomerDataSendSettingBy(customerId, dataType);
		if(null==efCustomerDataSendSetting){
			throw new BizException(StatusBizCode.CUSTOMER_HEALTH_DATA_SEND_SETT_MISSING.getValue());
		}
		if(efCustomerDataSendSetting.getMethod()==null||efCustomerDataSendSetting.getMethod().equalsIgnoreCase("GET")){
			String param=efDeviceHealthyDataPush.getHealthyData();
			String alarmUrl=efCustomerDataSendSetting.getSendUrl();
			String result=httpUtil.httpGet(alarmUrl, param);
			if(null!=result && result.contains("resultCode")){
				//JsonUtils.fromJson(result, BaseResult)
			}
		}else{
			//Map<String,String> paramMap=(Map<String,String>)JsonUtils.fromJson(efDeviceHealthyDataPush.getHealthyData(), Map.class);
			Map<String,String> paramMap = paramToMap(efDeviceHealthyDataPush.getHealthyData());
			String result = httpUtil.httpPost(efCustomerDataSendSetting.getSendUrl(), paramMap);
			
			log.info("发送健康数据返回结果  result = "+result+" ; ");
			
/*			Map map = JsonUtils.fromJson(result, Map.class);
			String status = map.get("status")!=null?map.get("status").toString():"1";
			String message = map.get("message")!=null?map.get("message").toString():"unknow error";
			if(!"0".equals(status)){
				throw new BizException(message);
			}*/
		}
		efDeviceHealthyDataPush.addSendNumber();
	}
	
	public Map<String,String> paramToMap(String param){
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isNotBlank(param)){
			String[] paramArr = param.split("&");
			for(String temp : paramArr){
				String[] tempArr = temp.split("="); 
				map.put(tempArr[0], tempArr[1]);
			}
		}
		
		return map;
		
	}
	
}
